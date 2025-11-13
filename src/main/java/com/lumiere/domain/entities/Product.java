package com.lumiere.domain.entities;

import com.lumiere.domain.entities.base.BaseEntity;
import com.lumiere.domain.vo.Money;
import com.lumiere.domain.vo.Stock;

import java.util.*;

public class Product extends BaseEntity {

    private String name;
    private String description;
    private Money price;
    private List<Rating> ratings;
    private Stock stock;

    private Product(UUID id, String name, String description, Money price, List<Rating> ratings, Stock stock) {

        super(id);
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.description = Objects.requireNonNull(description, "description cannot be null");
        this.price = Objects.requireNonNull(price, "price cannot be null");
        this.ratings = ratings != null ? new ArrayList<>(ratings) : new ArrayList<>();
        this.stock = Objects.requireNonNull(stock, "stock cannot be null");
    }

    // Getters

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Money getPrice() {
        return price;
    }

    public List<Rating> getRatings() {
        return Collections.unmodifiableList(ratings);
    }

    public Stock getStock() {
        return stock;
    }

    public Product increaseStock(int quantity) {
        this.stock = this.stock.add(quantity);
        return this;
    }

    public Product decreaseStock(int quantity) {
        this.stock = this.stock.subtract(quantity);
        return this;
    }

    public Product updatePrice(Money newPrice) {
        this.price = Objects.requireNonNull(newPrice, "price cannot be null");
        return this;
    }

    public Product addRating(Rating rating) {
        this.ratings.add(Objects.requireNonNull(rating, "rating cannot be null"));
        return this;
    }

    public void updateProduct(Optional<String> newName, Optional<String> newDescription) {
        newName.ifPresent(name -> this.name = name);
        newDescription.ifPresent(desc -> this.description = desc);
    }

    // Factory
    public static Product createProduct(String name, String description, Money price, Stock stock) {
        return new Product(null, name, description, price, null, stock);
    }

    public static Product from(UUID id, String name, String description, Money price, Stock stock) {
        return new Product(id, name, description, price, null, stock);
    }
}