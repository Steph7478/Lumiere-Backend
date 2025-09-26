package com.lumiere.application.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
                @NotBlank @Email String email,

                @NotBlank @Size(min = 3, max = 12) String name,

                @NotBlank @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$", message = "Password must be at least 8 characters long and contain letters, numbers, and a special character.") String password) {
}
