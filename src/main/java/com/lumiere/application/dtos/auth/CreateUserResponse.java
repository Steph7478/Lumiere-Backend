package com.lumiere.application.dtos.auth;

public record CreateUserResponse(
                String name,
                String accessToken,
                String refreshToken,
                String role) {
}
