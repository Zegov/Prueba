package com.sermaluc.prueba.apiuser.prueba.validator;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Value("${user.password.regex}")
    private String passwordRegex;

    @Value("${user.password.message}")
    private String passwordMessage;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        boolean isValid = Pattern.compile(passwordRegex).matcher(password).matches();
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(passwordMessage)
                  .addConstraintViolation();
        }
        return isValid;
    }

}
