package com.lumiere.application.dtos.admin.command.add;

import java.util.List;

import com.lumiere.domain.readmodels.ProductDetailReadModel;

public record AddProductOutput(
                List<ProductDetailReadModel> product) {
}