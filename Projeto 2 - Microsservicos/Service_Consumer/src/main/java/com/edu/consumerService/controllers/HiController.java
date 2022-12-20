package com.edu.consumerService.controllers;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/professor")
public class HiController {
    
    @GetMapping
    public String getMessage() {
        return "Hi!";
    }
}
