package com.lumiere.domain.entities;

import java.util.Objects;
import java.util.UUID;

import com.lumiere.domain.entities.base.BaseEntity;

public class Rating extends BaseEntity {

    private final UUID productId;
    private final UUID orderId;
    private final int rating;
    private final String comment;

    private Rating(UUID id, UUID productId, int rating, String comment, UUID orderId) {
        super(id);
        this.productId = Objects.requireNonNull(productId, "productId cannot be null");
        this.orderId = Objects.requireNonNull(orderId, "orderId cannot be null");
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
        this.rating = rating;
        this.comment = comment != null ? comment : "";
    }

    // Getters

    public UUID getProductId() {
        return productId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Rating updateComment(String newComment) {
        return new Rating(getId(), this.productId, this.rating, newComment, this.orderId);
    }

    public Rating updateRating(int newRating) {
        return new Rating(getId(), this.productId, newRating, this.comment, this.orderId);
    }

    // factory
    public static Rating createRating(UUID productId, int rating, String comment, UUID orderId) {
        return new Rating(null, productId, rating, comment, orderId);
    }

    public static Rating rebuild(UUID id, UUID productId, int rating, String comment, UUID orderId) {
        return new Rating(id, productId, rating, comment, orderId);
    }
}
