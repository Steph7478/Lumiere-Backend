package com.lumiere.application.dtos.product.query;

import com.lumiere.domain.enums.CategoriesEnum;
import com.lumiere.domain.vo.Money;

public record ProductSearchCriteria(
        String name,
        CategoriesEnum.Category category,
        CategoriesEnum.SubCategory subCategory,
        Money priceMin,
        Money priceMax,
        int page,
        int size,
        String sortBy) {
}