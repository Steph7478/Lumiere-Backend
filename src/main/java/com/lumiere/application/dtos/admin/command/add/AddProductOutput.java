package com.lumiere.application.dtos.admin.command.add;

import java.util.List;

import com.lumiere.domain.readmodels.ProductDetailReadModel;

import jakarta.validation.constraints.NotNull;

public record AddProductOutput(
        @NotNull List<ProductDetailReadModel> product) {
}