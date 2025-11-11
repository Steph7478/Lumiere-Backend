package com.lumiere.application.dtos.auth.command.update;

public record UpdateUserOutput(String message) {
    public UpdateUserOutput() {
        this("User updated successfully!");
    }
}