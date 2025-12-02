package com.lumiere.domain.readmodels;

import java.math.BigDecimal;
import java.util.UUID;

import com.lumiere.domain.enums.CategoriesEnum.Category;

public record OrderItemReadModel(UUID productId, String name, int quantity, BigDecimal unitPrice, String currency,
        Category category) {
}