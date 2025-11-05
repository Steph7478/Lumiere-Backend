package com.lumiere.application.dtos.auth;

import java.util.UUID;

public record GetMeResponse(
                UUID userId,
                String name,
                boolean isAdmin) {

}
