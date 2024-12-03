package com.spring.temp.email;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/sendEmail")
    public void sendEmail(String[] recipientsArray, String subject, String message) throws MessagingException {
        emailService.sendEmailToRelatedUsers(recipientsArray, !subject.isEmpty() ? subject : "defaultSubject", message);
    }

}
