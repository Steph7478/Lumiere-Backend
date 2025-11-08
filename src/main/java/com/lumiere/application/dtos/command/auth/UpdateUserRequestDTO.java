package com.lumiere.application.dtos.command.auth;

import java.util.Optional;

public record UpdateUserRequestDTO(
                Optional<String> name,
                Optional<String> email,
                Optional<String> newPassword) {
}