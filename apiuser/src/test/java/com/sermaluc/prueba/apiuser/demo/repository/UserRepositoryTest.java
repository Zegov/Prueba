package com.sermaluc.prueba.apiuser.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

import com.sermaluc.prueba.apiuser.prueba.model.Telefono;
import com.sermaluc.prueba.apiuser.prueba.model.User;
import com.sermaluc.prueba.apiuser.prueba.repository.UserRepository;
import com.sermaluc.prueba.apiuser.prueba.ApiUserApplication;

@DataJpaTest
@ContextConfiguration(classes = ApiUserApplication.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    
    @Test
    void whenSaveUser_thenUserIsPersistedWithAllFields() {
        User user = new User();
        user.setNombre("Eusebio Jimenez");
        user.setEmail("euji@prueba4.cl");
        user.setPassword("Password1&");
        user.setCreado(LocalDateTime.now());
        user.setModificado(LocalDateTime.now());
        user.setUltimoLogin(LocalDateTime.now());
        user.setToken(UUID.randomUUID().toString());
        user.setEstaActivo(true);

        Telefono phone = new Telefono();
        phone.setNumeroFono("1234567");
        phone.setCodCiudad("1");
        phone.setCodPais("57");
        user.setTelefonos(Arrays.asList(phone));

        User savedUser = userRepository.save(user);

        // Then
        assertNotNull(savedUser.getId());
        assertEquals("Eusebio Jimenez", savedUser.getNombre());
        assertEquals("euji@prueba4.cl", savedUser.getEmail());
        assertEquals(user.getPassword(), savedUser.getPassword());
        assertNotNull(savedUser.getCreado());
        assertNotNull(savedUser.getModificado());
        assertNotNull(savedUser.getUltimoLogin());
        assertNotNull(savedUser.getToken());
        assertTrue(savedUser.isEstaActivo());
        assertEquals(1, savedUser.getTelefonos().size());
    }

    @Test
    void whenFindByEmail_thenReturnUser() {

        User user = new User();
        user.setNombre("Maria Lopez");
        user.setEmail("malo@prueba5.cl");
        user.setPassword("Password2!");
        user.setCreado(LocalDateTime.now());
        user.setModificado(LocalDateTime.now());
        user.setUltimoLogin(LocalDateTime.now());
        user.setToken(UUID.randomUUID().toString());
        user.setEstaActivo(true);

        userRepository.save(user);

        User foundUser = userRepository.findByEmail("malo@prueba5.cl");

        assertNotNull(foundUser);
        assertEquals("Maria Lopez", foundUser.getNombre());
    }

    @Test
    void whenFindByNonExistentEmail_thenReturnNull() {
        // When
        User foundUser = userRepository.findByEmail("nonexistent@example.com");

        // Then
        assertNull(foundUser);
    }

    @Test
    void whenSaveUserWithDuplicateEmail_thenThrowException() {

        User user1 = new User();
        user1.setNombre("Eusebio Jimenez");
        user1.setEmail("euji@prueba4.cl");
        user1.setPassword("Password1&");
        user1.setCreado(LocalDateTime.now());
        user1.setModificado(LocalDateTime.now());
        user1.setUltimoLogin(LocalDateTime.now());
        user1.setToken(UUID.randomUUID().toString());
        user1.setEstaActivo(true);
        
        userRepository.save(user1);

        User user2 = new User();
        user2.setNombre("Maria Lopez");
        user2.setEmail("euji@prueba4.cl"); // mismo email que user1
        user2.setPassword("Password2!");
        user2.setCreado(LocalDateTime.now());
        user2.setModificado(LocalDateTime.now());
        user2.setUltimoLogin(LocalDateTime.now());
        user2.setToken(UUID.randomUUID().toString());
        user2.setEstaActivo(true);

        // Then
        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(user2);
            userRepository.flush();
        });
    }

    @Test
    void whenSaveUserWithPhones_thenPhonesArePersistedCorrectly() {
        // Given
        Telefono phone1 = new Telefono();
        phone1.setNumeroFono("1234567");
        phone1.setCodCiudad("1");
        phone1.setCodPais("57");

        Telefono phone2 = new Telefono();
        phone2.setNumeroFono("7654321");
        phone2.setCodCiudad("2");
        phone2.setCodPais("58");

        User user = new User();
        user.setNombre("Eusebio Jimenez");
        user.setEmail("euji@prueba4.cl");
        user.setPassword("Password1&");
        user.setCreado(LocalDateTime.now());
        user.setModificado(LocalDateTime.now());
        user.setUltimoLogin(LocalDateTime.now());
        user.setToken(UUID.randomUUID().toString());
        user.setEstaActivo(true);

        user.setTelefonos(Arrays.asList(phone1, phone2));


        // When
        User savedUser = userRepository.save(user);

        // Then
        assertEquals(2, savedUser.getTelefonos().size());
        assertTrue(savedUser.getTelefonos().stream()
            .anyMatch(p -> p.getNumeroFono().equals("1234567")));
        assertTrue(savedUser.getTelefonos().stream()
            .anyMatch(p -> p.getNumeroFono().equals("7654321")));
    }

    @Test
    void whenUpdateUser_thenFieldsAreUpdated() {
        // Given
        User user = new User();
        user.setNombre("Eusebio Jimenez");
        user.setEmail("euji@prueba4.cl");
        user.setPassword("Password1&");
        user.setCreado(LocalDateTime.now());
        user.setModificado(LocalDateTime.now());
        user.setUltimoLogin(LocalDateTime.now());
        user.setToken(UUID.randomUUID().toString());
        user.setEstaActivo(true);

        User savedUser = userRepository.save(user);
        
        // When
        savedUser.setNombre("Updated Name");
        savedUser.setModificado(savedUser.getCreado().plusSeconds(1));
        User updatedUser = userRepository.save(savedUser);

        // Then
        assertEquals("Updated Name", updatedUser.getNombre());
        assertTrue(updatedUser.getModificado().isAfter(updatedUser.getCreado()));
    }

    @Test
    void whenDeleteUser_thenUserIsRemoved() {
        // Given
        User user = new User();
        user.setNombre("Eusebio Jimenez");
        user.setEmail("euji@prueba4.cl");
        user.setPassword("Password1&");
        user.setCreado(LocalDateTime.now());
        user.setModificado(LocalDateTime.now());
        user.setUltimoLogin(LocalDateTime.now());
        user.setToken(UUID.randomUUID().toString());
        user.setEstaActivo(true);

        User savedUser = userRepository.save(user);

        // When
        userRepository.delete(savedUser);

        // Then
        assertFalse(userRepository.findById(savedUser.getId()).isPresent());
    }

    @Test
    void whenFindAllUsers_thenReturnUsersList() {
        // Given
        User user1 = new User();
        user1.setNombre("Eusebio Jimenez");
        user1.setEmail("euji@prueba4.cl");
        user1.setPassword("Password1&");
        user1.setCreado(LocalDateTime.now());
        user1.setModificado(LocalDateTime.now());
        user1.setUltimoLogin(LocalDateTime.now());
        user1.setToken(UUID.randomUUID().toString());
        user1.setEstaActivo(true);
        
        User user2 = new User();
        user2.setNombre("Maria Lopez");
        user2.setEmail("malo@prueba5.cl");
        user2.setPassword("Password2!");
        user2.setCreado(LocalDateTime.now());
        user2.setModificado(LocalDateTime.now());
        user2.setUltimoLogin(LocalDateTime.now());
        user2.setToken(UUID.randomUUID().toString());
        user2.setEstaActivo(true);

        userRepository.save(user1);
        userRepository.save(user2);

        // When
        List<User> users = userRepository.findAll();

        // Then
        assertEquals(2, users.size());
    }
}
