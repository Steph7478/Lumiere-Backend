package com.lumiere.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cart {

    private UUID id;
    private UUID userId;
    private Coupon cupon;
    private List<Product> cart;

    public Cart(UUID id, UUID userId, Coupon cupon, List<Product> cart) {
        this.id = id != null ? id : UUID.randomUUID();
        this.userId = userId;
        this.cupon = cupon;
        this.cart = cart != null ? cart : new ArrayList<>();
    }

    // getters

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public Coupon getCupon() {
        return cupon;
    }

    public List<Product> getCart() {
        return cart;
    }

    // setters

    public void setCupon(Coupon cupon) {
        this.cupon = cupon;
    }

    // helper
    public void addProduct(Product product) {
        if (product != null) {
            cart.add(product);
        }
    }

    public void removeProduct(Product product) {
        cart.remove(product);
    }

    // createDefault
    public static Cart createCart(UUID userId) {
        return new Cart(null, userId, null, new ArrayList<>());
    }
}
