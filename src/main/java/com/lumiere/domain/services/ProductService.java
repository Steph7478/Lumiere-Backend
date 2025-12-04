package com.lumiere.domain.services;

import java.util.Optional;
import java.util.UUID;

import com.lumiere.domain.entities.Product;
import com.lumiere.domain.factories.ProductFactory;
import com.lumiere.domain.vo.Money;
import com.lumiere.domain.vo.Stock;

public final class ProductService {

    private ProductService() {
    }

    public static Product create(UUID id, String name, String description, Money price, Stock stock, String image) {
        return ProductFactory.create(id, name, description, price, stock, image);
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
