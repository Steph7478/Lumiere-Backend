package com.lumiere.presentation.exceptions;

public class TokenGenerationException extends RuntimeException {
    public TokenGenerationException(Throwable cause) {
        super("Failed to generate tokens" + cause);
    }
}
