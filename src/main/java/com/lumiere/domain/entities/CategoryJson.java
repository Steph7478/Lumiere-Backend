package com.lumiere.domain.entities;

import com.lumiere.domain.enums.CategoriesEnum.Category;
import com.lumiere.domain.enums.CategoriesEnum.SubCategory;

public class CategoryJson {

    private Category name;
    private SubCategory subcategory;

    public CategoryJson() {
    }

    public CategoryJson(Category name, SubCategory subcategory) {
        this.name = name;
        this.subcategory = subcategory;
    }

    public Category getName() {
        return name;
    }

    public void setName(Category name) {
        this.name = name;
    }

    public SubCategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(SubCategory subcategory) {
        this.subcategory = subcategory;
    }
}