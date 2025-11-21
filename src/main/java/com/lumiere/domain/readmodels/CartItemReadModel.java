package com.lumiere.domain.readmodels;

import com.lumiere.domain.entities.Product;

public record CartItemReadModel(
        Product product,
        Integer quantity) {

}