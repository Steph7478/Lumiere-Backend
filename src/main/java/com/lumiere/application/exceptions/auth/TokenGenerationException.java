package com.lumiere.application.exceptions.auth;

import com.lumiere.application.exceptions.base.ApplicationException;

public class TokenGenerationException extends ApplicationException {
    public TokenGenerationException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
