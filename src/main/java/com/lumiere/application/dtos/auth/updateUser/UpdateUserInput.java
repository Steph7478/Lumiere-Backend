package com.lumiere.application.dtos.auth.updateUser;

import java.util.UUID;

public record UpdateUserInput(
        UpdateUserRequestDTO requestData,
        UUID userId) {
}