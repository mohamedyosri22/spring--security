package com.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class MainController {
    @GetMapping("/hi")
    public String hiStudent(){
        return "hi stupid !!";
    }
}
