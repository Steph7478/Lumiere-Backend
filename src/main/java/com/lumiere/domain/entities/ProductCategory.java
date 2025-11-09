package com.lumiere.domain.entities;

public class ProductCategory {

    private String productId;
    private String category;
    private String subcategory;

    public ProductCategory(String productId, String category, String subcategory) {
        this.productId = productId;
        this.category = category;
        this.subcategory = subcategory;
    }

    public String getProductId() {
        return productId;
    }

    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }

}
