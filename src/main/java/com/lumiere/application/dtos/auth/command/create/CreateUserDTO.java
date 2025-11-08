package com.lumiere.application.dtos.auth.command.create;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
        @NotBlank @Email String email,
        @NotBlank @Size(min = 3, max = 50, message = "Name must have between 3 to 50 characters") String name,

        @NotBlank @Size(min = 8, message = "Password must be at least 8 characters long") @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*.])(?=.{8,}).*$", message = "Password must be at least 8 characters long and contain letters, numbers, and a special character (e.g., !@#$%^&*.).") String password) {
}