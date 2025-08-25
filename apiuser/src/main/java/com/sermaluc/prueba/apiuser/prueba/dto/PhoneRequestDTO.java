package com.sermaluc.prueba.apiuser.prueba.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Información del teléfono del usuario")
@Data
public class PhoneRequestDTO {

    @Schema(description = "Número de teléfono", example = "1234567")
    @NotBlank(message = "El número de teléfono es obligatorio")
    @Pattern(regexp = "\\d{7,10}", message = "El número debe tener entre 7 y 10 dígitos")
    private String number;

    @Schema(description = "Código de ciudad", example = "1")
    @NotBlank(message = "El código de ciudad es obligatorio")
    @Pattern(regexp = "\\d{1,5}", message = "El código de ciudad debe tener entre 1 y 5 dígitos")
    private String cityCode;

    @Schema(description = "Código de país", example = "57")
    @NotBlank(message = "El código de país es obligatorio")
    @Pattern(regexp = "\\d{1,5}", message = "El código de país debe tener entre 1 y 5 dígitos")
    private String countryCode;

}
