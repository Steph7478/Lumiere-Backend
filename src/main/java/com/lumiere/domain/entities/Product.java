package com.lumiere.domain.entities;

import com.lumiere.domain.entities.base.BaseEntity;
import com.lumiere.domain.vo.Money;
import com.lumiere.domain.vo.Stock;

import java.util.*;

public class Product extends BaseEntity {

    private final String name;
    private final String description;
    private final Money price;
    private final List<Rating> ratings;
    private final Stock stock;

    private Product(UUID id, String name, String description, Money price, List<Rating> ratings, Stock stock) {

        super(id);
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.description = Objects.requireNonNull(description, "description cannot be null");
        this.price = Objects.requireNonNull(price, "price cannot be null");
        this.ratings = ratings != null ? new ArrayList<>(ratings) : new ArrayList<>();
        this.stock = Objects.requireNonNull(stock, "stock cannot be null");
    }

    // Getters
    public UUID getId() {
        return getId();
    }

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

    public Product adjustStock(int delta) {
        Stock newStock;

        if (delta > 0) {
            newStock = this.stock.add(delta);
        } else if (delta < 0) {
            newStock = this.stock.subtract(Math.abs(delta));
        } else {
            return this;
        }

        return new Product(getId(), this.name, this.description, this.price, this.ratings, newStock);
    }

    public Product increaseStock(int quantity) {
        return new Product(getId(), this.name, this.description, this.price, this.ratings, this.stock.add(quantity));
    }

    public Product decreaseStock(int quantity) {
        return new Product(getId(), this.name, this.description, this.price, this.ratings,
                this.stock.subtract(quantity));
    }

    public Product updatePrice(Money newPrice) {
        return new Product(getId(), this.name, this.description,
                Objects.requireNonNull(newPrice, "price cannot be null"), this.ratings, this.stock);
    }

    public Product addRating(Rating rating) {
        List<Rating> newRatings = new ArrayList<>(this.ratings);
        newRatings.add(Objects.requireNonNull(rating, "rating cannot be null"));
        return new Product(getId(), this.name, this.description, this.price, newRatings, this.stock);
    }

    // Factory
    public static Product createProduct(String name, String description, Money price, Stock stock) {
        return new Product(null, name, description, price, null, stock);
    }
}
