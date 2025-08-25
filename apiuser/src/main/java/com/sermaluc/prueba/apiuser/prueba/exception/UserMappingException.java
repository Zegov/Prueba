package com.sermaluc.prueba.apiuser.prueba.exception;

/**
 * Excepción específica para errores de mapeo entre DTOs y entidades.
 */
public class UserMappingException extends RuntimeException {
    public UserMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
