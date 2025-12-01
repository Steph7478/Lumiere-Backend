package com.lumiere.domain.services;

import java.util.List;
import java.util.Optional;

import com.lumiere.domain.entities.Product;
import com.lumiere.domain.factories.ProductFactory;
import com.lumiere.domain.vo.Money;

public abstract class ProductService {

    public static List<Product> createProducts(List<Product> products) {
        return products.stream()
                .map(p -> ProductFactory.create(
                        p.getName(),
                        p.getDescription(),
                        p.getPrice(),
                        p.getStock()))
                .toList();
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
