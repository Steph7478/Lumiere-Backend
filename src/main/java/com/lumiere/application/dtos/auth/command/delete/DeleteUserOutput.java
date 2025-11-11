package com.lumiere.application.dtos.auth.command.delete;

public record DeleteUserOutput(String message) {
    public DeleteUserOutput() {
        this("User deleted successfully!");
    }
}
