package com.stepanov.project03.controllers;

import com.stepanov.project03.models.Person;
import com.stepanov.project03.services.PersonDetailsService;
import com.stepanov.project03.services.RegistrationService;
import com.stepanov.project03.util.PersonValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonDetailsService personDetailsService;
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;

    public AuthController(PersonDetailsService personDetailsService, RegistrationService registrationService, PersonValidator personValidator) {
        this.personDetailsService = personDetailsService;
        this.registrationService = registrationService;
        this.personValidator = personValidator;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("person") Person person) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }
        registrationService.register(person);
        return "redirect:/auth/login";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        return "auth/admin";
    }
}
