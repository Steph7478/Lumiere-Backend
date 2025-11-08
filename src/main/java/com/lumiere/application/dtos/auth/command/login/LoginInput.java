package com.lumiere.application.dtos.auth.command.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginInput(
                @NotBlank @Email String email,
                @NotBlank @Size(min = 8, message = "Password must be at least 8 characters long") @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*.])(?=.{8,}).*$", message = "Password must be at least 8 characters long and contain letters, numbers, and a special character (e.g., !@#$%^&*.).") String password) {
}