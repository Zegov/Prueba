package com.sermaluc.prueba.apiuser.prueba.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Registro de Usuarios")
                        .version("1.0")
                        .description("API REST para el registro y gestión de usuarios")
                        .contact(new Contact()
                                .name("Diego Villegas A.")
                                .email("diego.villegas@sermaluc.cl")
                                .url("https://sermaluc.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }

}
