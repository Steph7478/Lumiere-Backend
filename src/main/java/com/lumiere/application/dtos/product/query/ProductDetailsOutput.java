package com.lumiere.application.dtos.product.query;

import java.util.List;
import org.springframework.data.domain.Page;
import com.lumiere.domain.readmodels.ProductDetailReadModel;

import jakarta.validation.constraints.NotNull;

public record ProductDetailsOutput(
        @NotNull List<ProductDetailReadModel> content,
        @NotNull int pageNumber,
        @NotNull int pageSize,
        @NotNull long totalElements,
        @NotNull int totalPages) {
    public static ProductDetailsOutput fromPage(Page<ProductDetailReadModel> page) {
        return new ProductDetailsOutput(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
}