package com.lumiere.presentation.controllers;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lumiere.application.dtos.product.query.ProductSearchCriteria;
import com.lumiere.application.handlers.FindProductsQueryHandler;
import com.lumiere.domain.enums.CategoriesEnum.*;
import com.lumiere.domain.readmodels.ProductDetailReadModel;
import com.lumiere.presentation.controllers.base.BaseController;
import com.lumiere.presentation.routes.Routes;

@RestController
public class ProductController extends BaseController {

    private final FindProductsQueryHandler queryHandler;

    public ProductController(FindProductsQueryHandler queryHandler) {
        this.queryHandler = queryHandler;
    }

    @GetMapping(Routes.PUBLIC.PRODUCTS.FILTER)
    public ResponseEntity<Page<ProductDetailReadModel>> findProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) SubCategory subCategory,
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

        Page<ProductDetailReadModel> resultPage = queryHandler.handle(criteria);
        return ResponseEntity.ok(resultPage);
    }
}