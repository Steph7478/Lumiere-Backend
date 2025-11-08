package com.lumiere.application.dtos.auth.response.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record CreateUserOutput(
                String name,
                @JsonIgnore String accessToken,
                @JsonIgnore String refreshToken,
                String role) {
}
