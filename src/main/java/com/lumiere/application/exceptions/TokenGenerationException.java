package com.lumiere.application.exceptions;

public class TokenGenerationException extends RuntimeException {
    public TokenGenerationException(Throwable cause) {
        super("Failed to generate tokens" + cause);
    }
}
