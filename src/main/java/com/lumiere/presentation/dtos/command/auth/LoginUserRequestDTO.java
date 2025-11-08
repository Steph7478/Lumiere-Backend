package com.lumiere.presentation.dtos.command.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUserRequestDTO(
        @NotBlank @Email String email,
        @NotBlank String password) {

}
