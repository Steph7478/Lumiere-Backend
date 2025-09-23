package com.lumiere.domain.repository;

import com.lumiere.domain.entity.Cart;

public interface CartRepository {
    Cart save(Cart cart);
}
