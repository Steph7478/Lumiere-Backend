package com.lumiere.application.dtos.auth.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginDTO(
                @NotBlank @Email String email,

                @NotBlank @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$", message = "Password must be at least 8 characters long and contain letters, numbers, and a special character.") String password) {
}
