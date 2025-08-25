package com.sermaluc.prueba.apiuser.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sermaluc.prueba.apiuser.prueba.dto.PhoneRequestDTO;
import com.sermaluc.prueba.apiuser.prueba.dto.UserDTO;
import com.sermaluc.prueba.apiuser.prueba.dto.UserRequestDTO;
import com.sermaluc.prueba.apiuser.prueba.exception.EmailExistsException;
import com.sermaluc.prueba.apiuser.prueba.exception.ValidationException;
import com.sermaluc.prueba.apiuser.prueba.mapper.UserMapper;
import com.sermaluc.prueba.apiuser.prueba.model.User;
import com.sermaluc.prueba.apiuser.prueba.repository.UserRepository;
import com.sermaluc.prueba.apiuser.prueba.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private UserRequestDTO validUserRequest;
    private User mockUser;
    private UserDTO mockUserDTO;

    @BeforeEach
    void setUp() {
        // Preparar datos de prueba
        PhoneRequestDTO phone = new PhoneRequestDTO();
        phone.setNumber("1234567");
        phone.setCityCode("9");
        phone.setCountryCode("56");

        validUserRequest = new UserRequestDTO();
        validUserRequest.setNombre("Test Mario Garrido");
        validUserRequest.setEmail("maga@sermaluc.com");
        validUserRequest.setPassword("Password123!");
        validUserRequest.setPhones(Collections.singletonList(phone));

        mockUser = new User();
        mockUser.setId(UUID.randomUUID());
        mockUser.setNombre("Test Mario Garrido");
        mockUser.setEmail("maga@sermaluc.com");
        mockUser.setPassword("hashedPassword");
        mockUser.setCreado(LocalDateTime.now());
        mockUser.setEstaActivo(true);

        mockUserDTO = new UserDTO();
        mockUserDTO.setId(mockUser.getId());
        mockUserDTO.setEmail(mockUser.getEmail());
        mockUserDTO.setActive(true);
    }

    @Test
    void whenRegisterUser_withValidData_thenReturnUserDTO() {
        // Given
        when(userRepository.findByEmail(validUserRequest.getEmail())).thenReturn(null);
        when(userMapper.toEntity(validUserRequest)).thenReturn(mockUser);
        when(passwordEncoder.encode(validUserRequest.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        when(userMapper.toDTO(mockUser)).thenReturn(mockUserDTO);

        // When
        UserDTO result = userService.registerUser(validUserRequest);

        // Then
        assertNotNull(result);
        assertEquals(mockUserDTO.getId(), result.getId());
        assertEquals(mockUserDTO.getEmail(), result.getEmail());
        assertTrue(result.isActive());

        verify(userRepository).findByEmail(validUserRequest.getEmail());
        verify(passwordEncoder).encode(validUserRequest.getPassword());
        verify(userRepository).save(any(User.class));
        verify(userMapper).toEntity(validUserRequest);
        verify(userMapper).toDTO(mockUser);
    }

    @Test
    void whenRegisterUser_withExistingEmail_thenThrowEmailExistsException() {
        // Given
        when(userRepository.findByEmail(validUserRequest.getEmail())).thenReturn(mockUser);

        // When & Then
        assertThrows(EmailExistsException.class, () -> {
            userService.registerUser(validUserRequest);
        });

        verify(userRepository).findByEmail(validUserRequest.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void whenRegisterUser_withNullRequest_thenThrowValidationException() {
        // When & Then
        assertThrows(ValidationException.class, () -> {
            userService.registerUser(null);
        });

        verify(userRepository, never()).findByEmail(any());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void whenValidateUserData_withNullData_thenThrowValidationException() {
        // When & Then
        assertThrows(ValidationException.class, () -> {
            userService.registerUser(null);
        });
    }
}
