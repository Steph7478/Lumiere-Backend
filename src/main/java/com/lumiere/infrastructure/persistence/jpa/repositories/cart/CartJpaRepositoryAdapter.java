package com.lumiere.infrastructure.persistence.jpa.repositories.cart;

import org.springframework.stereotype.Repository;

import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.repositories.CartRepository;
import com.lumiere.infrastructure.mappers.CartMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.CartJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.base.BaseRepositoryAdapter;

import jakarta.persistence.EntityManager;

@Repository
public class CartJpaRepositoryAdapter extends BaseRepositoryAdapter<Cart, CartJpaEntity> implements CartRepository {

    protected CartJpaRepositoryAdapter(CartJpaRepository jpaRepository,
            CartMapper mapper, EntityManager entityManager) {
        super(jpaRepository, mapper, entityManager, CartJpaEntity.class);
    }
}
