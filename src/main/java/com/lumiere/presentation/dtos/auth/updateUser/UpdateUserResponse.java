package com.lumiere.presentation.dtos.auth.updateUser;

public record UpdateUserResponse(
        String message) {
    public UpdateUserResponse() {
        this("User updated successfully!");
    }
}
