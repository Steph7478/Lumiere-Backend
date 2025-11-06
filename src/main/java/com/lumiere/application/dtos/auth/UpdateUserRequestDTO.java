package com.lumiere.application.dtos.auth;

import java.util.Optional;

public record UpdateUserRequestDTO(
        Object auth,
        Optional<String> name,
        Optional<String> email,
        Optional<String> newPassword) {
}