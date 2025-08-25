package com.sermaluc.prueba.apiuser.prueba.exception;

/**
 * Excepción específica para errores de acceso a datos durante operaciones de usuario.
 */
public class UserDataAccessException extends RuntimeException {
    public UserDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
