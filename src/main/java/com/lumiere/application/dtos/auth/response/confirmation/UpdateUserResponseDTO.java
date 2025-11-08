package com.lumiere.application.dtos.auth.response.confirmation;

public record UpdateUserResponseDTO(String message) {
    public UpdateUserResponseDTO() {
        this("User updated successfully!");
    }
}