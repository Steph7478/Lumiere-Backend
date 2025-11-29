package com.lumiere.domain.entities;

import java.util.UUID;
import com.lumiere.domain.entities.base.BaseEntity;
import com.lumiere.domain.enums.CategoriesEnum.Category;
import com.lumiere.domain.enums.CategoriesEnum.SubCategory;

public class ProductCategory extends BaseEntity {

    private CategoryJson category;

    public ProductCategory() {
        super(null);
    }

    public ProductCategory(UUID productId, Category category, SubCategory subcategory) {
        super(productId);
        this.category = new CategoryJson(category, subcategory);
    }

    public CategoryJson getCategory() {
        return category;
    }

    public void setCategory(CategoryJson category) {
        this.category = category;
    }
}