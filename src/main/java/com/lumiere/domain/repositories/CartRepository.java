package com.lumiere.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.repositories.base.BaseRepository;

public interface CartRepository extends BaseRepository<Cart> {
    Optional<Cart> findCartByUserId(UUID id);
}
