package com.lumiere.application.dtos.auth.updateUser;

public record UpdateUserResponseDTO(
        String message) {
    public UpdateUserResponseDTO() {
        this("User updated successfully!");
    }
}