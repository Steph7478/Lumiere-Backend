package com.lumiere.application.dtos.command.auth;

import java.util.UUID;

public record GetMeRequest(UUID userId) {
}
