package com.lumiere.domain.services;

import java.util.Optional;

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

    public static void update(Product product, Optional<String> name, Optional<String> description) {
        product.updateProduct(name, description);
    }

    public static Product increaseStock(Product product, int quantity) {
        return product.increaseStock(quantity);
    }

    public static Product decreaseStock(Product product, int quantity) {
        return product.decreaseStock(quantity);
    }

    public static Product updatePrice(Product product, Money newPrice) {
        return product.updatePrice(newPrice);
    }
}
