package com.lumiere.domain.entity;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Product {

    private final UUID id;
    private final String name;
    private final String description;
    private final Category category;
    private final SubCategory subCategory;
    private final CurrencyType currency;
    private Rating rating;
    private BigDecimal price;
    private int stock;

    private Product(UUID id, String name, String description, Category category,
            SubCategory subCategory, Rating rating, BigDecimal price,
            CurrencyType currency, int stock) {

        this.id = id != null ? id : UUID.randomUUID();
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.description = Objects.requireNonNull(description, "description cannot be null");
        this.category = Objects.requireNonNull(category, "category cannot be null");
        this.subCategory = Objects.requireNonNull(subCategory, "subCategory cannot be null");
        this.rating = rating;
        this.price = Objects.requireNonNull(price, "price cannot be null");
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("price cannot be negative");
        }
        this.currency = Objects.requireNonNull(currency, "currency cannot be null");
        if (stock < 0) {
            throw new IllegalArgumentException("stock cannot be negative");
        }
        this.stock = stock;
    }

    // Getters
    public UUID getId() {
        return id;
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

    public Rating getRating() {
        return rating;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public int getStock() {
        return stock;
    }

    public Product adjustStock(int delta) {
        int newStock = this.stock + delta;
        if (newStock < 0) {
            throw new IllegalArgumentException("stock cannot be negative");
        }
        return new Product(this.id, this.name, this.description, this.category,
                this.subCategory, this.rating, this.price, this.currency, newStock);
    }

    public Product updatePrice(BigDecimal newPrice) {
        Objects.requireNonNull(newPrice, "newPrice cannot be null");
        if (newPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("price cannot be negative");
        }
        return new Product(this.id, this.name, this.description, this.category,
                this.subCategory, this.rating, newPrice, this.currency, this.stock);
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

    // factory
    public static Product createProduct(String name, String description, Category category,
            SubCategory subCategory, BigDecimal price,
            CurrencyType currency, int stock) {
        return new Product(null, name, description, category, subCategory, null, price, currency, stock);
    }
}
