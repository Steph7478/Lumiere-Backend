package com.lumiere.application.dtos.auth;

public record CreateUserResponse(
        String email,
        String accessToken,
        String refreshToken,
        String role) {
}
