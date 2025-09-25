package com.lumiere.domain.entities;

import java.util.Objects;
import java.util.UUID;

public class Rating {

    private final UUID id;
    private final UUID productId;
    private final UUID orderId;
    private final int rating;
    private final String comment;

    private Rating(UUID id, UUID productId, int rating, String comment, UUID orderId) {
        this.id = id != null ? id : UUID.randomUUID();
        this.productId = Objects.requireNonNull(productId, "productId cannot be null");
        this.orderId = Objects.requireNonNull(orderId, "orderId cannot be null");
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
        this.rating = rating;
        this.comment = comment != null ? comment : "";
    }

    // Getters
    public UUID getId() {
        return id;
    }

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
        return new Rating(this.id, this.productId, this.rating, newComment, this.orderId);
    }

    public Rating updateRating(int newRating) {
        return new Rating(this.id, this.productId, newRating, this.comment, this.orderId);
    }

    // factory
    public static Rating createRating(UUID productId, int rating, String comment, UUID orderId) {
        return new Rating(null, productId, rating, comment, orderId);
    }
}
