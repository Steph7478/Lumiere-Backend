package com.lumiere.infrastructure.persistence.jpa.repositories.cart;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.repositories.CartRepository;
import com.lumiere.infrastructure.mappers.CartMapper;
import com.lumiere.infrastructure.mappers.CartItemMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.CartItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.CartJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.base.BaseRepositoryAdapter;
import com.lumiere.infrastructure.persistence.jpa.repositories.product.ProductJpaRepository;
import com.lumiere.infrastructure.persistence.jpa.repositories.user.UserJpaRepository;

import jakarta.persistence.EntityManager;

@Repository
public class CartJpaRepositoryAdapter extends BaseRepositoryAdapter<Cart, CartJpaEntity> implements CartRepository {

    private final ProductJpaRepository productJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final CartItemMapper cartItemMapper;
    private final CartJpaRepository cartJpaRepository;
    private final CartMapper cartMapper;

    protected CartJpaRepositoryAdapter(
            CartJpaRepository jpaRepository,
            CartMapper mapper,
            EntityManager entityManager,
            ProductJpaRepository productJpaRepository,
            UserJpaRepository userJpaRepository,
            CartItemMapper cartItemMapper) {
        super(jpaRepository, mapper, entityManager, CartJpaEntity.class);

        this.cartJpaRepository = Objects.requireNonNull(jpaRepository, "jpaRepository cannot be null");
        this.cartMapper = Objects.requireNonNull(mapper, "mapper cannot be null");
        this.productJpaRepository = Objects.requireNonNull(productJpaRepository, "productJpaRepository cannot be null");
        this.userJpaRepository = Objects.requireNonNull(userJpaRepository, "userJpaRepository cannot be null");
        this.cartItemMapper = Objects.requireNonNull(cartItemMapper, "cartItemMapper cannot be null");
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cart> findCartByUserId(UUID id) {
        return cartJpaRepository.findByUserId(id).map(cartMapper::toDomain);
    }

    @Override
    @Transactional
    public Cart save(Cart domain) {
        Objects.requireNonNull(domain, "domain cannot be null");

        CartJpaEntity jpaEntity = cartMapper.toJpa(
                domain,
                userJpaRepository,
                productJpaRepository,
                cartItemMapper);

        for (CartItemJpaEntity item : jpaEntity.getItems()) {
            item.setCartReference(jpaEntity);
        }

        CartJpaEntity saved = cartJpaRepository.save(jpaEntity);

        return cartMapper.toDomain(saved);
    }

    @Override
    @Transactional
    public Cart update(Cart domain) {
        return save(domain);
    }
}