package com.lumiere.application.dtos.auth.command.delete.output;

public record DeleteUserOutput(String message) {
    public DeleteUserOutput() {
        this("User deleted successfully!");
    }
}
