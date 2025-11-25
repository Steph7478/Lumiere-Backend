package com.lumiere.domain.readmodels;

import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;

public record CartItemReadModel(
                ProductJpaEntity product,
                int quantity) {
}
