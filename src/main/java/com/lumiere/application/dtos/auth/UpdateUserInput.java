package com.lumiere.application.dtos.auth;

import java.util.UUID;

public record UpdateUserInput(
                UpdateUserRequestDTO requestData,
                UUID userId) {
}