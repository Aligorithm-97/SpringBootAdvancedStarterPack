package com.spring.temp.auth;

import com.spring.temp.email.EmailService;
import com.spring.temp.email.EmailTemplateName;
import com.spring.temp.domain.repository.RoleRepository;
import com.spring.temp.exception.SomethingWentWrongException;
import com.spring.temp.exception.UserNotFoundException;
import com.spring.temp.security.JwtService;
import com.spring.temp.domain.model.Token;
import com.spring.temp.domain.repository.TokenRepository;
import com.spring.temp.domain.model.User;
import com.spring.temp.domain.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final EmailService emailService;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;
    public void register(RegistrationRequest request) throws MessagingException {
        var userRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(()-> new IllegalStateException("ROLE USER not found !"));

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();

        userRepository.save(user);

        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);


        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );

    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode();
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(30))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode() {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i< 6; i++ ){
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.fullName());
        claims.put("uid", user.getId());
        var jwtToken = jwtService.generateToken(claims, user);
        var jwtRefreshToken = jwtService.generateRefreshToken(claims, user);

        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", jwtToken)
                .maxAge(1800)
                .sameSite("none")
                .path("/")
                .secure(true)
                .build();
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", jwtRefreshToken)
                .httpOnly(true)
                .maxAge(3 * 60 * 60)
                .sameSite("none")
                .secure(true)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .body(AuthenticationResponse.builder().token(jwtToken).build());
    }

    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token).orElseThrow(()->new RuntimeException("Invalid Token"));

        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())){
            sendValidationEmail((savedToken.getUser()));
            throw new RuntimeException("Activation Token has expired. A new token has been sent!");
        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(()->new UsernameNotFoundException("User not found!"));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);

    }

    public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = WebUtils.getCookie(request, "refreshToken");
        if (cookie == null) {
            throw new SomethingWentWrongException("Refresh token not found in cookies.");
        }
        String jwtRefreshToken = cookie.getValue();
        if (jwtService.isTokenExpired(jwtRefreshToken)) {
            throw new SomethingWentWrongException("Token expired.");
        }
        String userEmail = jwtService.extractUserName(jwtRefreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        Optional<User> byEmail = userRepository.findByEmail(userEmail);
        if (byEmail.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }
        User user = byEmail.get();
        boolean tokenValid = jwtService.isTokenValid(jwtRefreshToken, userDetails);
        if (!tokenValid) {
            throw new SomethingWentWrongException("The provided refresh token is invalid.");
        }
        var claims = new HashMap<String, Object>();
        claims.put("fullName", user.fullName());
        claims.put("uid", user.getId());
        String generatedToken = jwtService.generateToken(claims, userDetails);
        Cookie generatedTokenCookie = new Cookie("accessToken", generatedToken);
        generatedTokenCookie.setPath("/");
        generatedTokenCookie.setMaxAge(1800);
        response.addCookie(generatedTokenCookie);
        return AuthenticationResponse.builder().token(generatedToken).build();
    }


}
