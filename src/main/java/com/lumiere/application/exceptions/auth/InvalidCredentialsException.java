package com.lumiere.application.exceptions.auth;

import com.lumiere.application.exceptions.base.ApplicationException;

public class InvalidCredentialsException extends ApplicationException {
    public InvalidCredentialsException() {
        super("Invalid credentials");
    }
}
