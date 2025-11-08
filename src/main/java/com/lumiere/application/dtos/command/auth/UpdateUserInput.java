package com.lumiere.application.dtos.command.auth;

import java.util.UUID;

public record UpdateUserInput(
        UpdateUserRequestDTO requestData,
        UUID userId) {
}