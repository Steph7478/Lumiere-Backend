package com.lumiere.application.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthResponseDTO(
        @NotBlank String userId,
        @NotBlank String name) {
}
