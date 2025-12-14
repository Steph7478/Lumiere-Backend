package com.lumiere.infrastructure.stripe.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StripeCheckoutSessionPayload(

        @JsonProperty("client_reference_id") String orderId,

        @JsonProperty("metadata") Metadata metadata,

        @JsonProperty("payment_intent") String paymentId,

        @JsonProperty("created") long createdAt,

        @JsonProperty("payment_method_types") List<String> paymentMethod) {
    public String userId() {
        return metadata.userId();
    }

    record Metadata(
            @JsonProperty("userId") String userId) {
    }
}
