package com.lumiere.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

    private final UUID id;
    private String name;
    private String description;
    private Category category;
    private SubCategory subCategory;
    private Rating rating;
    private BigDecimal price;
    private CurrencyType currency;
    private int stock;

    public Product(UUID id, String name, String description, Category category,
            SubCategory subCategory, Rating rating, BigDecimal price,
            CurrencyType currency, int stock) {
        this.id = id != null ? id : UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.category = category;
        this.subCategory = subCategory;
        this.rating = rating;
        this.price = price;
        this.currency = currency;
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

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    public void setStock(int stock) {
        this.stock = stock;
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

    public enum Rating {
        LOW, MEDIUM, HIGH
    }
}
