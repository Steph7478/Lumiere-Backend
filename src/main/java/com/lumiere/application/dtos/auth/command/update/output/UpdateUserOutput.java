package com.lumiere.application.dtos.auth.command.update.output;

public record UpdateUserOutput(String message) {
    public UpdateUserOutput() {
        this("User updated successfully!");
    }
}