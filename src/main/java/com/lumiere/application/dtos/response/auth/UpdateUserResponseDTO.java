package com.lumiere.application.dtos.response.auth;

public record UpdateUserResponseDTO(
        String message) {
    public UpdateUserResponseDTO() {
        this("User updated successfully!");
    }
}