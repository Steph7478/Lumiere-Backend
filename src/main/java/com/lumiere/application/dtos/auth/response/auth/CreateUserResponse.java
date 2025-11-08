package com.lumiere.application.dtos.auth.response.auth;

public record CreateUserResponse(
                String name,
                String accessToken,
                String refreshToken,
                String role) {
}
