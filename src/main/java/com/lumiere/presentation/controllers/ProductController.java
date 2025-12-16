package com.lumiere.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lumiere.application.dtos.product.query.ProductDetailsOutput;
import com.lumiere.application.dtos.product.query.ProductFindAllCriteria;
import com.lumiere.application.dtos.product.query.ProductSearchCriteria;
import com.lumiere.application.usecases.product.ProductReadUseCase;
import com.lumiere.domain.enums.CategoriesEnum;
import com.lumiere.domain.readmodels.ProductDetailReadModel;
import com.lumiere.presentation.routes.Routes;
import com.lumiere.shared.annotations.api.ApiVersion;
import com.lumiere.shared.annotations.cache.CacheableResponse;

@RestController
@CacheableResponse(maxAgeInSeconds = 900, sMaxAgeInSeconds = 3600)
public class ProductController {

    private final ProductReadUseCase productReadUseCase;

    public ProductController(ProductReadUseCase productReadUseCase) {
        this.productReadUseCase = productReadUseCase;
    }

    @ApiVersion("1")
    @Operation(summary = "Get product details by ID")
    @GetMapping(Routes.PUBLIC.PRODUCTS.BASE + "/{id}")
    public ResponseEntity<ProductDetailReadModel> getProductDetailById(
            @PathVariable UUID id) {
        return productReadUseCase.findDetailById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ApiVersion("1")
    @Operation(summary = "Search products by criteria")
    @GetMapping(Routes.PUBLIC.PRODUCTS.FILTER)
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
                name, category, subCategory, priceMin, priceMax, page, size, sortBy);

        ProductDetailsOutput output = productReadUseCase.findByCriteria(criteria);
        return ResponseEntity.ok(output);
    }

    @ApiVersion("1")
    @Operation(summary = "Get all products")
    @GetMapping(Routes.PUBLIC.PRODUCTS.BASE)
    public ResponseEntity<ProductDetailsOutput> findAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy) {

        ProductFindAllCriteria criteria = new ProductFindAllCriteria(page, size, sortBy);
        ProductDetailsOutput output = productReadUseCase.findAllProducts(criteria);
        return ResponseEntity.ok(output);
    }
}
