package com.lumiere.application.dtos.auth.command.update;

import java.util.Optional;

import jakarta.validation.constraints.*;

public record UpdateUserRequestData(
                @NotBlank Optional<String> name,
                @NotBlank @Email Optional<String> email,
                @NotBlank @Size(min = 8, message = "Password must be at least 8 characters long") @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*.])(?=.{8,}).*$", message = "Password must be at least 8 characters long and contain letters, numbers, and a special character (e.g., !@#$%^&*.).") Optional<String> password) {

        public boolean isCompleteUpdate() {
                return name.isPresent() &&
                                email.isPresent() &&
                                password.isPresent();
        }

        public boolean hasUpdates() {
                return name.isPresent() ||
                                email.isPresent() ||
                                password.isPresent();
        }
}