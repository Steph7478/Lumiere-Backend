package com.lumiere.presentation.dtos.auth.createUser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequestDTO(
        @NotBlank @Email String email,
        @NotBlank String name,
        @NotBlank String password) {
}
