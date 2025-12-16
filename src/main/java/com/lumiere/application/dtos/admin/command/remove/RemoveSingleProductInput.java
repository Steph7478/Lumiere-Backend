package com.lumiere.application.dtos.admin.command.remove;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record RemoveSingleProductInput(
        @NotNull UUID productId) {
}
