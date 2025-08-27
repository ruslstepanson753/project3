package com.stepanov.project03.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @Value("${message}")
    private String message;

    @GetMapping("/")
    public String MyController(Model model) {
        model.addAttribute("message", message);
        return "index";
    }
}
