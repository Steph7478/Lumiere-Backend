package com.lumiere.application.exceptions;

public class InvalidCredentialsException extends ApplicationException {
    public InvalidCredentialsException() {
        super("Invalid credentials");
    }
}
