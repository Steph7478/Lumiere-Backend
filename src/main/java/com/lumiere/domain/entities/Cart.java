package com.lumiere.domain.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.lumiere.domain.entities.base.BaseEntity;
import com.lumiere.domain.vo.CartItem;

public class Cart extends BaseEntity {

    private final User user;
    private final List<CartItem> items;

    public Cart(UUID id, User user, List<CartItem> items) {
        super(id);
        this.user = Objects.requireNonNull(user, "User ID cannot be null");
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public Cart addProduct(UUID productId, int quantityToAdd) {
        if (productId == null || quantityToAdd <= 0) {
            return this;
        }

        List<CartItem> newItems = new ArrayList<>(this.items);

        Optional<CartItem> existingItem = newItems.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem oldItem = existingItem.get();
            newItems.remove(oldItem);
            int newQuantity = oldItem.getQuantity() + quantityToAdd;

            newItems.add(oldItem.withQuantity(newQuantity));
        } else {
            newItems.add(new CartItem(productId, quantityToAdd));
        }

        return new Cart(getId(), this.user, newItems);
    }

    public Cart removeProduct(UUID productId) {
        if (productId == null)
            return this;

        List<CartItem> newItems = new ArrayList<>(this.items);
        newItems.removeIf(item -> item.getProductId().equals(productId));

        return new Cart(getId(), this.user, newItems);
    }

    public static Cart createCart(User user) {
        return new Cart(UUID.randomUUID(), user, new ArrayList<>());
    }
}