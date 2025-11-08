package com.lumiere.presentation.dtos.response.auth;

public record UpdateUserResponse(
        String message) {
    public UpdateUserResponse() {
        this("User updated successfully!");
    }
}
