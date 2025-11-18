package com.lumiere.application.dtos.product.query;

import java.util.List;
import org.springframework.data.domain.Page;
import com.lumiere.domain.readmodels.ProductDetailReadModel;

public record ProductDetailsOutput(
        List<ProductDetailReadModel> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages) {
    public static ProductDetailsOutput fromPage(Page<ProductDetailReadModel> page) {
        return new ProductDetailsOutput(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
}