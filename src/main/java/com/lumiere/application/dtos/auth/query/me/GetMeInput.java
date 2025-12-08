package com.lumiere.application.dtos.auth.query.me;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record GetMeInput(@NotNull UUID userId) {
}
