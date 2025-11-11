package com.lumiere.domain.services;

import com.lumiere.domain.entities.Product;
import com.lumiere.domain.vo.Money;
import com.lumiere.domain.vo.Stock;

public class ProductService {
    private ProductService() {
    }

    public static Product createProduct(
            String name,
            String description,
            Money price,
            Stock stock) {

        return Product.createProduct(name, description, price, stock);
    }

}
