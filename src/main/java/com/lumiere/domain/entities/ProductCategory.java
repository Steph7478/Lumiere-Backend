package com.lumiere.domain.entities;

import java.util.UUID;

import com.lumiere.domain.entities.base.BaseEntity;
import com.lumiere.domain.enums.CategoriesEnum.Category;
import com.lumiere.domain.enums.CategoriesEnum.SubCategory;

public class ProductCategory extends BaseEntity {

    private Category category;
    private SubCategory subcategory;

    public ProductCategory() {
        super(null);
    }

    public ProductCategory(UUID productId, Category category, SubCategory subcategory) {
        super(productId);
        this.category = category;
        this.subcategory = subcategory;
    }

    public Category getCategory() {
        return category;
    }

    public SubCategory getSubcategory() {
        return subcategory;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setSubcategory(SubCategory subcategory) {
        this.subcategory = subcategory;
    }
}