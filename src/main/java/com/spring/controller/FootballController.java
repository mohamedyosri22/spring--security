package com.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/football")
public class FootballController {
    @GetMapping("/")
    public String start(){
        return "this is football controller";
    }
}
