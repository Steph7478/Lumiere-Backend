package com.lumiere.application.dtos.auth.command.logout;

public record LogoutOutput(String message) {
    public LogoutOutput() {
        this("Logout successfully");
    }
}