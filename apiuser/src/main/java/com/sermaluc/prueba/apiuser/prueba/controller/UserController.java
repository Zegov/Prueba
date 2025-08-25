package com.sermaluc.prueba.apiuser.prueba.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sermaluc.prueba.apiuser.prueba.dto.UserDTO;
import com.sermaluc.prueba.apiuser.prueba.dto.UserRequestDTO;
import com.sermaluc.prueba.apiuser.prueba.exception.EmailExistsException;
import com.sermaluc.prueba.apiuser.prueba.exception.UserRegistrationException;
import com.sermaluc.prueba.apiuser.prueba.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador REST para la gestión de usuarios.
 * 
 * Este controlador maneja todas las operaciones relacionadas con usuarios,
 * incluyendo el registro de nuevos usuarios con validación de datos,
 * generación de tokens y persistencia en base de datos.
 * 
 * @author Diego Villegas
 * @version 1.0
 * @since 22-08-2025
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "API para gestión de usuarios")
public class UserController {

    private final UserService userService;


    /**
     * Constructor del controlador de usuarios.
     * 
     * @param userService Servicio de usuarios para la lógica de negocio
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * Este endpoint permite registrar un nuevo usuario validando que:
     * - El email no esté previamente registrado
     * - La contraseña cumpla con los requisitos de seguridad
     * - Los datos del usuario sean válidos según las reglas de negocio
     * - Al menos un teléfono sea proporcionado
     * 
     * El sistema genera automáticamente:
     * - Un token único para el usuario
     * - Timestamps de creación, modificación y último login
     * - Codificación segura de la contraseña
     * - Estado activo por defecto
     * 
     * @param userRequest DTO con los datos del usuario a registrar (nombre, email, contraseña, teléfonos)
     * @return ResponseEntity con el usuario creado y código HTTP 201, o error con código 400/500
     * @throws EmailExistsException si el email ya está registrado
     * @throws ValidationException si los datos de entrada no son válidos
     * @throws UserRegistrationException si ocurre un error durante el proceso de registro
     */
    @Operation(
        summary = "Registrar un nuevo usuario",
        description = "Crea un nuevo usuario con la información proporcionada"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Usuario creado exitosamente",
            content = @Content(schema = @Schema(implementation = UserDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos inválidos o email ya registrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserRequestDTO userRequest) {
        
        UserDTO createdUser = userService.registerUser(userRequest);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Clase interna para representar respuestas de error.
     * 
     * Utilizada para proporcionar mensajes de error estructurados
     * en las respuestas HTTP cuando ocurren excepciones.
     */
    private static class ErrorResponse {
        private String mensaje;

        /**
         * Constructor de ErrorResponse.
         * 
         * @param mensaje Mensaje descriptivo del error ocurrido
         */
        public ErrorResponse(String mensaje) {
            this.mensaje = mensaje;
        }

    }

}
