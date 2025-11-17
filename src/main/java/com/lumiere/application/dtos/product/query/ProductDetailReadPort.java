package com.lumiere.application.dtos.product.query;

import org.springframework.data.domain.Page;

import com.lumiere.domain.readmodels.ProductDetailReadModel;

public interface ProductDetailReadPort {
    Page<ProductDetailReadModel> findProductsByCriteria(ProductSearchCriteria criteria);
}