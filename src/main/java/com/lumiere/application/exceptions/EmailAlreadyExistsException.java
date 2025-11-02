package com.lumiere.application.exceptions;

public class EmailAlreadyExistsException extends ApplicationException {
    public EmailAlreadyExistsException(String email) {
        super("This email already exists: " + email);
    }
}
