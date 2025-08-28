package com.stepanov.project03.util;

import com.stepanov.project03.models.Person;
import com.stepanov.project03.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@Component
public class PersonValidator implements Validator {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        try {
            // Пытаемся найти пользователя
            UserDetails userDetails = personDetailsService.loadUserByUsername(person.getUsername());
            // Если пользователь найден - это ошибка (username уже занят)
            errors.rejectValue("username", "", "Такой пользователь уже существует");
        } catch (UsernameNotFoundException e) {
            // Пользователь не найден - это хорошо (username свободен)
            return;
        }
    }
}
