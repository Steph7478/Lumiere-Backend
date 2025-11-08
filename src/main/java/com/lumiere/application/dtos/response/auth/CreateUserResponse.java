package com.lumiere.application.dtos.response.auth;

public record CreateUserResponse(
                String name,
                String accessToken,
                String refreshToken,
                String role) {
}
