package com.lumiere.application.ports;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.lumiere.application.dtos.product.query.ProductSearchCriteria;
import com.lumiere.domain.readmodels.ProductDetailReadModel;

public interface ProductDetailReadPort {
    Page<ProductDetailReadModel> findProductsByCriteria(ProductSearchCriteria criteria);

    Optional<ProductDetailReadModel> findProductDetailById(UUID id);
}