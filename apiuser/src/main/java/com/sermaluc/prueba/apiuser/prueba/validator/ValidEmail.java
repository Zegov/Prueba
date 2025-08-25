package com.sermaluc.prueba.apiuser.prueba.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)

public @interface ValidEmail {

    String message() default "El formato del email no es válido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
