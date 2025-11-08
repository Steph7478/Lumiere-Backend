package com.lumiere.application.dtos.auth.response.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record LoginResponse(@JsonIgnore String accessToken, @JsonIgnore String refreshToken, String name, String role) {
}
