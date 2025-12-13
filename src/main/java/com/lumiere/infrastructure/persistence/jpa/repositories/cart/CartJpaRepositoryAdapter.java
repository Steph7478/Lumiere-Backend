package com.lumiere.infrastructure.persistence.jpa.repositories.cart;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.repositories.CartRepository;
import com.lumiere.infrastructure.mappers.CartMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.CartJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.base.BaseRepositoryAdapter;

import jakarta.persistence.EntityManager;

@Repository
public class CartJpaRepositoryAdapter extends BaseRepositoryAdapter<Cart, CartJpaEntity> implements CartRepository {

    private final CartJpaRepository cartJpaRepository;
    private final CartMapper cartMapper;

    protected CartJpaRepositoryAdapter(
            CartJpaRepository jpaRepository,
            CartMapper mapper,
            EntityManager entityManager) {
        super(jpaRepository, mapper, entityManager);

        this.cartJpaRepository = jpaRepository;
        this.cartMapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cart> findCartByUserId(UUID id) {
        return cartJpaRepository.findByUserId(id).map(cartMapper::toDomain);
    }
}