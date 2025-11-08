package com.lumiere.application.exceptions.auth;

import com.lumiere.application.exceptions.base.ApplicationException;

public class EmailAlreadyExistsException extends ApplicationException {
    public EmailAlreadyExistsException(String email) {
        super("This email already exists: " + email);
    }
}
