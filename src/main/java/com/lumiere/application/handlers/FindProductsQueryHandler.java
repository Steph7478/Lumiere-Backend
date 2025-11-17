package com.lumiere.application.handlers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.product.query.ProductDetailReadPort;
import com.lumiere.application.dtos.product.query.ProductSearchCriteria;
import com.lumiere.domain.readmodels.ProductDetailReadModel;

@Service
public class FindProductsQueryHandler {

    private final ProductDetailReadPort productReadService;

    public FindProductsQueryHandler(ProductDetailReadPort productReadService) {
        this.productReadService = productReadService;
    }

    public Page<ProductDetailReadModel> handle(ProductSearchCriteria criteria) {
        if (criteria.page() < 0)
            throw new IllegalArgumentException("Invalid page");

        return productReadService.findProductsByCriteria(criteria);
    }
}