package com.sermaluc.prueba.apiuser.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sermaluc.prueba.apiuser.prueba.controller.UserController;
import com.sermaluc.prueba.apiuser.prueba.dto.PhoneRequestDTO;
import com.sermaluc.prueba.apiuser.prueba.dto.UserDTO;
import com.sermaluc.prueba.apiuser.prueba.dto.UserRequestDTO;
import com.sermaluc.prueba.apiuser.prueba.exception.EmailExistsException;
import com.sermaluc.prueba.apiuser.prueba.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserRequestDTO validUserRequest;
    private UserDTO mockUserDTO;

    @BeforeEach
    void setUp() {
        PhoneRequestDTO phone = new PhoneRequestDTO();
        phone.setNumber("1234567");
        phone.setCityCode("32");
        phone.setCountryCode("56");

        validUserRequest = new UserRequestDTO();
        validUserRequest.setNombre("Test Jean Durand");
        validUserRequest.setEmail("jedu@sermaluc.com");
        validUserRequest.setPassword("Password123!");
        validUserRequest.setPhones(Collections.singletonList(phone));

        mockUserDTO = new UserDTO();
        mockUserDTO.setId(UUID.randomUUID());
        mockUserDTO.setEmail("jedu@sermaluc.com");
        mockUserDTO.setActive(true);
    }

    @Test
    void whenRegisterUser_withValidRequest_thenReturnCreatedStatus() {
        // Given
        when(userService.registerUser(any(UserRequestDTO.class))).thenReturn(mockUserDTO);

        // When
        ResponseEntity<UserDTO> response = userController.registerUser(validUserRequest);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockUserDTO.getId(), response.getBody().getId());
        assertEquals(mockUserDTO.getEmail(), response.getBody().getEmail());
        assertTrue(response.getBody().isActive());

        verify(userService, times(1)).registerUser(validUserRequest);
    }

    @Test
    void whenRegisterUser_withDuplicateEmail_thenThrowException() {
        // Given
        when(userService.registerUser(any(UserRequestDTO.class)))
            .thenThrow(new EmailExistsException("El correo ya se encuentra registrado"));

        // When & Then
        assertThrows(EmailExistsException.class, () -> {
            userController.registerUser(validUserRequest);
        });

        verify(userService, times(1)).registerUser(validUserRequest);
    }

    @Test
    void whenRegisterUser_callsServiceCorrectly() {
        // Given
        when(userService.registerUser(validUserRequest)).thenReturn(mockUserDTO);

        // When
        userController.registerUser(validUserRequest);

        // Then
        verify(userService).registerUser(validUserRequest);
        verifyNoMoreInteractions(userService);
    }

}
