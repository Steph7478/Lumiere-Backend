package com.lumiere.application.dtos.auth.createUser;

public record CreateUserResponse(
        String name,
        String accessToken,
        String refreshToken,
        String role) {
}
