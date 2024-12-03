package com.spring.temp.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @NotEmpty(message = "FirstName required !")
    @NotBlank(message = "FirstName required !")
    private String firstName;
    @NotEmpty(message = "LastName required !")
    @NotBlank(message = "LastName required !")
    private String lastName;
    @Email(message = "Email format required !")
    @NotEmpty(message = "Email required !")
    @NotBlank(message = "Email required !")
    private String email;
    @NotEmpty(message = "Password required !")
    @NotBlank(message = "Password required !")
    @Size(min = 8,message = "Min 8 Characters !")
    private String password;

}
