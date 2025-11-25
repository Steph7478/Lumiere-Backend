package com.lumiere.domain.readmodels;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemReadModel(UUID productId, String name, int quantity, BigDecimal unitPrice, String currency) {
}