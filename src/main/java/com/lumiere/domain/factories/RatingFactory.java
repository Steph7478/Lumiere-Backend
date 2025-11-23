package com.lumiere.domain.factories;

import java.util.UUID;

import com.lumiere.domain.entities.Rating;

public class RatingFactory {

    public static Rating from(UUID id, UUID productId, int rating, String comment, UUID orderId) {
        return new Rating(id, productId, rating, comment, orderId);
    }

    public static Rating create(UUID productId, int rating, String comment, UUID orderId) {
        return new Rating(null, productId, rating, comment, orderId);
    }
}