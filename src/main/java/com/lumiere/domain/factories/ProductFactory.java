package com.lumiere.domain.factories;

import com.lumiere.domain.entities.Product;
import com.lumiere.domain.entities.Rating;
import com.lumiere.domain.vo.Money;
import com.lumiere.domain.vo.Stock;

import java.util.List;
import java.util.UUID;

public class ProductFactory {

    public static Product from(UUID id, String name, String description, Money price, List<Rating> ratings,
            Stock stock) {
        return new Product(id, name, description, price, ratings, stock);
    }

    public static Product create(String name, String description, Money price, Stock stock) {
        return new Product(null, name, description, price, null, stock);
    }
}