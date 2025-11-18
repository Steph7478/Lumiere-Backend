package com.lumiere.application.dtos.product.query.filter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.lumiere.domain.readmodels.ProductDetailReadModel;

public interface ProductDetailReadPort {
    Page<ProductDetailReadModel> findProductsByCriteria(ProductSearchCriteria criteria);

    Optional<ProductDetailReadModel> findProductDetailById(UUID id);
}