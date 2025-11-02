package com.lumiere.domain.entities;

import com.lumiere.domain.entities.base.BaseEntity;
import com.lumiere.domain.vo.Money;
import java.util.*;

public class Product extends BaseEntity {

    private final String name;
    private final String description;
    private final Category category;
    private final SubCategory subCategory;
    private final Money price;
    private final List<Rating> ratings;
    private final int stock;

    private Product(UUID id, String name, String description, Category category,
            SubCategory subCategory, Money price, List<Rating> ratings, int stock) {

        super(id);
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.description = Objects.requireNonNull(description, "description cannot be null");
        this.category = Objects.requireNonNull(category, "category cannot be null");
        this.subCategory = Objects.requireNonNull(subCategory, "subCategory cannot be null");
        this.price = Objects.requireNonNull(price, "price cannot be null");
        this.ratings = ratings != null ? new ArrayList<>(ratings) : new ArrayList<>();
        if (stock < 0)
            throw new IllegalArgumentException("stock cannot be negative");
        this.stock = stock;
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

    public Category getCategory() {
        return category;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public Money getPrice() {
        return price;
    }

    public List<Rating> getRatings() {
        return Collections.unmodifiableList(ratings);
    }

    public int getStock() {
        return stock;
    }

    // Métodos de domínio
    public Product adjustStock(int delta) {
        int newStock = this.stock + delta;
        if (newStock < 0)
            throw new IllegalArgumentException("stock cannot be negative");
        return new Product(getId(), this.name, this.description, this.category,
                this.subCategory, this.price, this.ratings, newStock);
    }

    public Product updatePrice(Money newPrice) {
        return new Product(getId(), this.name, this.description, this.category,
                this.subCategory, Objects.requireNonNull(newPrice, "price cannot be null"), this.ratings, this.stock);
    }

    public Product addRating(Rating rating) {
        List<Rating> newRatings = new ArrayList<>(this.ratings);
        newRatings.add(Objects.requireNonNull(rating, "rating cannot be null"));
        return new Product(getId(), this.name, this.description, this.category,
                this.subCategory, this.price, newRatings, this.stock);
    }

    // Enums
    public enum Category {
        ELECTRONICS, CLOTHING, BOOKS
    }

    public enum SubCategory {
        PHONE, LAPTOP, SHIRT, NOVEL
    }

    public enum CurrencyType {
        USD, EUR, BRL
    }

    // Factory
    public static Product createProduct(String name, String description, Category category,
            SubCategory subCategory, Money price, int stock) {
        return new Product(null, name, description, category, subCategory, price, null, stock);
    }
}
