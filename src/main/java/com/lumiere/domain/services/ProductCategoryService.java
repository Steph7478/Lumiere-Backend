package com.lumiere.domain.services;

import java.util.UUID;

import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.enums.CategoriesEnum.Category;
import com.lumiere.domain.enums.CategoriesEnum.SubCategory;
import com.lumiere.domain.factories.ProductCategoryFactory;

public abstract class ProductCategoryService {
    private ProductCategoryService() {
    }

    public static ProductCategory createProductCategory(UUID productId, Category category, SubCategory subcategory) {
        return ProductCategoryFactory.from(productId, category, subcategory);
    }
}
