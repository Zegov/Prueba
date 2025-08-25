package com.sermaluc.prueba.apiuser.prueba.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Respuesta con la información del usuario registrado")
@Data
public class UserDTO {

    @Schema(description = "Identificador único del usuario", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@example.com")
    private String email;

    @Schema(description = "Fecha de creación del usuario", example = "2024-01-01T10:00:00")
    private LocalDateTime created;

    @Schema(description = "Fecha de última modificación del usuario", example = "2024-01-02T12:00:00")
    private LocalDateTime modified;

    @Schema(description = "Fecha y hora del último inicio de sesión", example = "2024-01-03T14:30:00")
    private LocalDateTime lastLogin;

    @Schema(description = "Token de acceso del usuario", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "Indica si el usuario está activo", example = "true")
    @JsonProperty("isActive")
    private boolean isActive;

    @Schema(description = "Lista de teléfonos asociados al usuario")
    private List<PhoneDTO> phones;

}
