package com.sermaluc.prueba.apiuser.prueba.exception;

public class EmailExistsException extends RuntimeException {
    public EmailExistsException(String message) {
        super(message);
    }
}
