package com.lumiere.application.dtos.response.auth;

public record LoginResponse(String accessToken, String refreshToken, String name, String role) {
}
