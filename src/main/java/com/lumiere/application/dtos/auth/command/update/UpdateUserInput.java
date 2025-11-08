package com.lumiere.application.dtos.auth.command.update;

import java.util.UUID;

public record UpdateUserInput(
                UpdateUserRequestData requestData,
                UUID userId) {
}