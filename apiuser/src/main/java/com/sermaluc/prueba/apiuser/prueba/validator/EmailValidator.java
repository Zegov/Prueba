package com.sermaluc.prueba.apiuser.prueba.validator;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class EmailValidator implements ConstraintValidator<ValidEmail, String>{

    @Value("${user.email.regex}")
    private String emailRegex;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return false;
        }
        return Pattern.compile(emailRegex).matcher(email).matches();
    }
}
