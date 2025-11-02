package com.lumiere.application.exceptions;

public class TokenGenerationException extends ApplicationException {
    public TokenGenerationException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
