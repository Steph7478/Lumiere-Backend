package com.lumiere.presentation.dtos;

import java.util.UUID;

public record GetUserResponse(
        String name,
        UUID userId) {
}
