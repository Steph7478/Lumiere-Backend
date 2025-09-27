package com.lumiere.application.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("This email already exist" + email);
    }
}
