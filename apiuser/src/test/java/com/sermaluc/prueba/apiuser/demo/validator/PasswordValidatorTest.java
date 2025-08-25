package com.sermaluc.prueba.apiuser.demo.validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import jakarta.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.sermaluc.prueba.apiuser.prueba.validator.PasswordValidator;

@ExtendWith(MockitoExtension.class)
public class PasswordValidatorTest {

    private PasswordValidator passwordValidator;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder;

    @BeforeEach
    void setUp() {
        passwordValidator = new PasswordValidator();
        // Simular las propiedades
        ReflectionTestUtils.setField(passwordValidator, "passwordRegex", 
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        ReflectionTestUtils.setField(passwordValidator, "passwordMessage", 
            "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un carácter especial");
    }

    @Test
    void whenIsValid_withValidPassword_thenReturnTrue() {
        // Given
        String validPassword = "Password123!";

        // When
        boolean result = passwordValidator.isValid(validPassword, context);

        // Then
        assertTrue(result);
        verify(context, never()).disableDefaultConstraintViolation();
    }

    @Test
    void whenIsValid_withInvalidPassword_thenReturnFalse() {
        // Given
        String invalidPassword = "123456"; // No tiene mayúsculas, minúsculas ni caracteres especiales
        when(context.buildConstraintViolationWithTemplate(any())).thenReturn(violationBuilder);

        // When
        boolean result = passwordValidator.isValid(invalidPassword, context);

        // Then
        assertFalse(result);
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate(any());
    }

    @Test
    void whenIsValid_withNullPassword_thenReturnFalse() {
        // When
        boolean result = passwordValidator.isValid(null, context);

        // Then
        assertFalse(result);
    }
}
