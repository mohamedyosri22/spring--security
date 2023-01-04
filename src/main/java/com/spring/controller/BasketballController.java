package com.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basketball")
public class BasketballController {
    @GetMapping("/")
    public String start(){
        return "this is basketball controller";
    }
}
