package com.lumiere.application.dtos.auth;

public record UpdateUserResponseDTO(
        String message) {
    public UpdateUserResponseDTO() {
        this("User update successfully!");
    }
}