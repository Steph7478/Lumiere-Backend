package com.lumiere.application.dtos.auth.query.logout.output;

public record LogoutOutput(String message) {
    public LogoutOutput() {
        this("Logout successfully");
    }
}