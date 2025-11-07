package com.lumiere.application.dtos.auth.login;

public record LoginResponse(String accessToken, String refreshToken, String name, String role) {
}
