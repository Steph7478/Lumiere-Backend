package com.lumiere.application.exceptions.auth;

import com.lumiere.application.exceptions.base.ApplicationException;

public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException() {
        super("User not found");
    }
}
