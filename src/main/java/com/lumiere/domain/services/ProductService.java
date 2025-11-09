package com.lumiere.domain.services;

import com.lumiere.domain.entities.Product;
import com.lumiere.domain.enums.CategoriesEnum.*;
import com.lumiere.domain.vo.Money;
import com.lumiere.domain.vo.ActingUser;

public class ProductService {

    public Product createProduct(ActingUser actingUser,
            String name,
            String description,
            Category category,
            SubCategory subCategory,
            Money price,
            int stock) {

        return Product.createProduct(name, description, category, subCategory, price, stock);
    }

}
