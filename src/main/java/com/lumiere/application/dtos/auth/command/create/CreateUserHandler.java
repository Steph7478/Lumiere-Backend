package com.lumiere.application.dtos.auth.command.create;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record CreateUserHandler(
        String name,
        @JsonIgnore String accessToken,
        @JsonIgnore String refreshToken,
        String role) {
}
