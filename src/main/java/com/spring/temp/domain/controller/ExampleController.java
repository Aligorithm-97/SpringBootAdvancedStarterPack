package com.spring.temp.domain.controller;

import com.spring.temp.domain.service.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("example")
public class ExampleController {

    private final ExampleService exampleService;

    @GetMapping("/getAll")
    public void getAll(){
        exampleService.getAll();
    }

}
