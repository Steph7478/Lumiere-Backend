package com.lumiere.application.dtos.product.query;

import java.math.BigDecimal;

import com.lumiere.domain.enums.CategoriesEnum;

import jakarta.validation.constraints.*;

public record ProductSearchCriteria(
        @NotBlank String name,
        @NotNull CategoriesEnum.Category category,
        @NotNull CategoriesEnum.SubCategory subCategory,
        @NotNull BigDecimal priceMin,
        @NotNull BigDecimal priceMax,
        @NotNull int page,
        @NotNull int size,
        @NotBlank String sortBy) {
}