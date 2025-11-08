package com.lumiere.application.dtos.auth.command.update;

import java.util.Optional;

public record UpdateUserRequestDTO(
                Optional<String> name,
                Optional<String> email,
                Optional<String> newPassword) {

        public boolean isCompleteUpdate() {
                return name.isPresent() &&
                                email.isPresent() &&
                                newPassword.isPresent();
        }

        public boolean hasUpdates() {
                return name.isPresent() ||
                                email.isPresent() ||
                                newPassword.isPresent();
        }
}