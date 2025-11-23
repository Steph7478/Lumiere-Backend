package com.lumiere.domain.factories;

import java.util.UUID;

import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.enums.CategoriesEnum.Category;
import com.lumiere.domain.enums.CategoriesEnum.SubCategory;

public class ProductCategoryFactory {

    public static ProductCategory from(UUID productId, Category category, SubCategory subcategory) {
        return new ProductCategory(productId, category, subcategory);
    }
}