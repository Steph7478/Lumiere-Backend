package com.lumiere.presentation.dtos.auth.updateUser;

import java.util.Optional;

public record UpdateUserRequest(
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