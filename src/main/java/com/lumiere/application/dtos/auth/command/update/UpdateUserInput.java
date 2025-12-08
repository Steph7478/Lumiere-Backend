package com.lumiere.application.dtos.auth.command.update;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record UpdateUserInput(
        UpdateUserRequestData requestData,
        @NotNull UUID userId) {
}