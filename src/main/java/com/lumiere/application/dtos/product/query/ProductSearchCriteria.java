package com.lumiere.application.dtos.product.query;

import java.math.BigDecimal;

import com.lumiere.domain.enums.CategoriesEnum;

public record ProductSearchCriteria(
                String name,
                CategoriesEnum.Category category,
                CategoriesEnum.SubCategory subCategory,
                BigDecimal priceMin,
                BigDecimal priceMax,
                int page,
                int size,
                String sortBy) {
}