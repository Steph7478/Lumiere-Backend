package com.lumiere.domain.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.lumiere.domain.entities.base.BaseEntity;

public class Cart extends BaseEntity {

    private final UUID userId;
    private final Coupon coupon;
    private final List<Product> products;

    public Cart(UUID id, UUID userId, Coupon coupon, List<Product> products) {
        super(id);
        this.userId = Objects.requireNonNull(userId, "User cannot be null");
        this.coupon = coupon;
        this.products = products != null ? new ArrayList<>(products) : new ArrayList<>();
    }

    // Getters

    public UUID getUserId() {
        return userId;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public Cart withCoupon(Coupon coupon) {
        return new Cart(getId(), this.userId, coupon, this.products);
    }

    public Cart addProduct(Product product) {
        if (product == null)
            return this;

        List<Product> newProducts = new ArrayList<>(this.products);
        newProducts.add(product);

        return new Cart(getId(), this.userId, this.coupon, newProducts);
    }

    public Cart removeProduct(Product product) {
        if (product == null)
            return this;

        List<Product> newProducts = new ArrayList<>(this.products);
        newProducts.remove(product);

        return new Cart(getId(), this.userId, this.coupon, newProducts);
    }

    // factory
    public static Cart createCart(UUID userId) {
        return new Cart(null, userId, null, new ArrayList<>());
    }
}
