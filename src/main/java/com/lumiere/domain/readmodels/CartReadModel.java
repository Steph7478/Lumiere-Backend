package com.lumiere.domain.readmodels;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CartReadModel(
                UUID id,
                List<CartItemReadModel> items,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {
}
