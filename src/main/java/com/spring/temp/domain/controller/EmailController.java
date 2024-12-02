package com.spring.temp.domain.controller;

import com.spring.temp.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("email")
public class EmailController {

    private final EmailService emailService;


    @GetMapping("/getEmail")
    public void getEmail(){
        System.out.println("get");
    }

    @PostMapping("/sendEmail")
    public void sendEmail(){
        System.out.println("send");
    }



}
