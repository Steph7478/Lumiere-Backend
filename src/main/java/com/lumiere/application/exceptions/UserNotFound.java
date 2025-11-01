package com.lumiere.application.exceptions;

public class UserNotFound extends RuntimeException {
    public UserNotFound() {
        super("User not found");
    }
}
