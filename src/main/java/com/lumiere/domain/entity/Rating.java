package com.lumiere.domain.entity;

import java.util.UUID;

public class Rating {

    private final UUID id;
    private final UUID productId;
    private int rating;
    private String comment;
    private final UUID orderId;

    public Rating(UUID id, UUID productId, int rating, String comment, UUID orderId) {
        this.id = id;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
        this.orderId = orderId;
    }

    // getters

    public UUID getId() {
        return id;
    }

    public UUID getProductId() {
        return productId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public UUID getOrderId() {
        return orderId;
    }

    // setters

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRating(int rating) {
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
        this.rating = rating;
    }
}
