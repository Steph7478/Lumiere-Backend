package com.lumiere.presentation.controllers;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lumiere.application.dtos.product.query.filter.ProductDetailsOutput;
import com.lumiere.application.dtos.product.query.filter.ProductSearchCriteria;
import com.lumiere.application.usecases.product.ProductReadUseCase;
import com.lumiere.domain.enums.CategoriesEnum;
import com.lumiere.domain.readmodels.ProductDetailReadModel;
import com.lumiere.presentation.controllers.base.BaseController;
import com.lumiere.presentation.routes.Routes;

@RestController
public class ProductController extends BaseController {

    private final ProductReadUseCase productReadUseCase;

    public ProductController(ProductReadUseCase productReadUseCase) {
        this.productReadUseCase = productReadUseCase;
    }

    @GetMapping(Routes.PUBLIC.PRODUCTS.BASE + "/{id}")
    public ResponseEntity<ProductDetailReadModel> getProductDetailById(@PathVariable UUID id) {
        return productReadUseCase.findDetailById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(Routes.PUBLIC.PRODUCTS.BASE)
    public ResponseEntity<ProductDetailsOutput> findProductsByCriteria(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) CategoriesEnum.Category category,
            @RequestParam(required = false) CategoriesEnum.SubCategory subCategory,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy) {

        ProductSearchCriteria criteria = new ProductSearchCriteria(
                name,
                category,
                subCategory,
                priceMin,
                priceMax,
                page,
                size,
                sortBy);

        ProductDetailsOutput output = productReadUseCase.findByCriteria(criteria);
        return ResponseEntity.ok(output);
    }
}