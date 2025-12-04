package com.lumiere.domain.factories;

import com.lumiere.domain.entities.Product;
import com.lumiere.domain.entities.Rating;
import com.lumiere.domain.vo.Money;
import com.lumiere.domain.vo.Stock;

import java.util.List;
import java.util.UUID;

public class ProductFactory {

    public static Product from(UUID id, String name, String description, Money price, List<Rating> ratings,
            Stock stock, String image) {
        return new Product(id, name, description, price, ratings, stock, image);
    }

    public static Product create(UUID id, String name, String description, Money price, Stock stock, String image) {
        return new Product(id, name, description, price, null, stock, image);
    }
}