package com.lumiere.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

import com.lumiere.infrastructure.persistence.jpa.entities.base.BaseJpaEntity;

@Getter
@Setter
@Entity
@Table(name = "ratings")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class RatingJpaEntity extends BaseJpaEntity implements Serializable {

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    private Integer rating;

    private String comment;

    public RatingJpaEntity(
            UUID id,
            UUID productId,
            UUID orderId,
            Integer rating,
            String comment) {
        super(id);
        this.productId = productId;
        this.orderId = orderId;
        this.rating = rating;
        this.comment = comment;
    }
}