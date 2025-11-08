package com.lumiere.application.dtos.auth.command.logout.output;

public record LogoutOutput(String message) {
    public LogoutOutput() {
        this("Logout successfully");
    }
}