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

    @FunctionalInterface
    private interface ItemOperation {
        int apply(int currentQuantity, int modificationQuantity);
    }

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

        return this.updateItem(productId, quantityToAdd,
                (current, modification) -> current + modification,
                true);
    }

    public Cart removeProduct(UUID productId, int quantityToRemove) {
        if (productId == null || quantityToRemove <= 0) {
            return this;
        }

        return this.updateItem(productId, quantityToRemove,
                (current, modification) -> current - modification,
                false);
    }

    private Cart updateItem(UUID productId, int modificationQuantity, ItemOperation operation,
            boolean shouldAddIfMissing) {
        List<CartItem> newItems = new ArrayList<>(this.items);

        Optional<CartItem> existingItem = newItems.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem oldItem = existingItem.get();
            newItems.remove(oldItem);

            int newQuantity = operation.apply(oldItem.getQuantity(), modificationQuantity);

            if (newQuantity > 0) {
                newItems.add(oldItem.withQuantity(newQuantity));
            }

            return new Cart(getId(), this.user, newItems);

        } else if (shouldAddIfMissing && modificationQuantity > 0) {
            newItems.add(new CartItem(productId, modificationQuantity));
            return new Cart(getId(), this.user, newItems);
        }

        return this;
    }
}