package com.lumiere.domain.repositories;

import com.lumiere.domain.entities.Cart;

public interface CartRepository {
    Cart save(Cart cart);
}
