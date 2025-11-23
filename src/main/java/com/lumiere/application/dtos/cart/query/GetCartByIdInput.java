package com.lumiere.application.dtos.cart.query;

import java.util.Optional;
import java.util.UUID;

public record GetCartByIdInput(UUID userId, Optional<UUID> cartId) {
}
