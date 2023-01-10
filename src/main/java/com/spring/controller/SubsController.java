package com.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subs")
public class SubsController {
    @PostMapping("/start")
    public String start(){
        return "this is subscribers controller";
    }

    @GetMapping("/end")
    public String end(){
        return "this is subscribers controller";
    }
}
