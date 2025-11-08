package com.lumiere.application.dtos.auth.command.update;

import java.util.Optional;

public record UpdateUserRequestDTO(
        Optional<String> name,
        Optional<String> email,
        Optional<String> newPassword) {
}