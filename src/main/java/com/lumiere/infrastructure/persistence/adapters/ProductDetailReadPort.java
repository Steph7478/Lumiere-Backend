package com.lumiere.infrastructure.persistence.adapters;

import com.lumiere.application.dtos.product.query.filter.ProductSearchCriteria;
import com.lumiere.domain.readmodels.ProductDetailReadModel;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface ProductDetailReadPort {

    Page<ProductDetailReadModel> findProductsByCriteria(ProductSearchCriteria criteria);

    Optional<ProductDetailReadModel> findProductDetailById(UUID id);
}