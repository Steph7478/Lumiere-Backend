package com.lumiere.domain.readmodels;

import java.math.BigDecimal;

import com.lumiere.domain.entities.Product;

public record OrderItemReadModel(Product product, String name, int quantity, BigDecimal unitPrice) {
}