package com.lumiere.domain.services;

import java.util.UUID;

import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.enums.CategoriesEnum.Category;
import com.lumiere.domain.enums.CategoriesEnum.SubCategory;

public class ProductCategoryService {
    private ProductCategoryService() {
    }

    public static ProductCategory createProductCategory(UUID productId, Category category, SubCategory subcategory) {
        return ProductCategory.createProductCategory(productId, category, subcategory);
    }
}
