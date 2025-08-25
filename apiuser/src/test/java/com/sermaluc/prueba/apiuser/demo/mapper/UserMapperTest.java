package com.sermaluc.prueba.apiuser.demo.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sermaluc.prueba.apiuser.prueba.dto.PhoneRequestDTO;
import com.sermaluc.prueba.apiuser.prueba.dto.UserDTO;
import com.sermaluc.prueba.apiuser.prueba.dto.UserRequestDTO;
import com.sermaluc.prueba.apiuser.prueba.mapper.UserMapper;
import com.sermaluc.prueba.apiuser.prueba.model.Telefono;
import com.sermaluc.prueba.apiuser.prueba.model.User;


public class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    void whenToDTO_withValidUser_thenReturnUserDTO() {
        // Given
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("test@example.com");
        user.setCreado(LocalDateTime.now());
        user.setModificado(LocalDateTime.now());
        user.setUltimoLogin(LocalDateTime.now());
        user.setToken("test-token");
        user.setEstaActivo(true);

        Telefono phone = new Telefono("1234567", "1", "57");
        user.setTelefonos(Collections.singletonList(phone));

        // When
        UserDTO result = userMapper.toDTO(user);

        // Then
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getCreado(), result.getCreated());
        assertEquals(user.getModificado(), result.getModified());
        assertEquals(user.getUltimoLogin(), result.getLastLogin());
        assertEquals(user.getToken(), result.getToken());
        assertTrue(result.isActive());
        assertEquals(1, result.getPhones().size());
        assertEquals("1234567", result.getPhones().get(0).getNumber());
    }

    @Test
    void whenToDTO_withNullUser_thenReturnNull() {
        // When
        UserDTO result = userMapper.toDTO(null);

        // Then
        assertNull(result);
    }

    @Test
    void whenToEntity_withValidUserRequestDTO_thenReturnUser() {
        // Given
        PhoneRequestDTO phoneDTO = new PhoneRequestDTO();
        phoneDTO.setNumber("1234567");
        phoneDTO.setCityCode("1");
        phoneDTO.setCountryCode("57");

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setNombre("Test User");
        userRequestDTO.setEmail("test@example.com");
        userRequestDTO.setPassword("password");
        userRequestDTO.setPhones(Collections.singletonList(phoneDTO));

        // When
        User result = userMapper.toEntity(userRequestDTO);

        // Then
        assertNotNull(result);
        assertEquals(userRequestDTO.getNombre(), result.getNombre());
        assertEquals(userRequestDTO.getEmail(), result.getEmail());
        assertEquals(userRequestDTO.getPassword(), result.getPassword());
        assertEquals(1, result.getTelefonos().size());
        assertEquals("1234567", result.getTelefonos().get(0).getNumeroFono());
    }

    @Test
    void whenToEntity_withNullDTO_thenReturnNull() {
        // When
        User result = userMapper.toEntity(null);

        // Then
        assertNull(result);
    }

}
