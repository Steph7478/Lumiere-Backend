package com.lumiere.application.dtos.auth.response.confirmation;

public record DeleteUserResponse(String message) {
    public DeleteUserResponse() {
        this("User deleted successfully!");
    }
}
