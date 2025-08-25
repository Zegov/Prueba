package com.sermaluc.prueba.apiuser.prueba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Información del teléfono del usuario")
@Data
public class PhoneDTO {

    @Schema(description = "Número de teléfono", example = "1234567")
    private String number;

    @Schema(description = "Código de ciudad", example = "1")
    private String cityCode;

    @Schema(description = "Código de país", example = "57")
    private String countryCode;

}
