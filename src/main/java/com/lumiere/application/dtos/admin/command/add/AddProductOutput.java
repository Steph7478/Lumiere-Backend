package com.lumiere.application.dtos.admin.command.add;

import com.lumiere.domain.readmodels.ProductDetailReadModel;

public record AddProductOutput(
        ProductDetailReadModel product) {
}