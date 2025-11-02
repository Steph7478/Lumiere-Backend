package com.lumiere.application.dtos.auth;

public record LoginResponse(String accessToken, String refreshToken, String name, String role) {
}
