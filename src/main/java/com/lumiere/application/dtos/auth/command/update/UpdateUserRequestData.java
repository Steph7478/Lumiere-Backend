package com.lumiere.application.dtos.auth.command.update;

import java.util.Optional;

public record UpdateUserRequestData(
                Optional<String> name,
                Optional<String> email,
                Optional<String> password) {

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