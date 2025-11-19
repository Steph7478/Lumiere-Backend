package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Cart;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.CartJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.CartItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.UserJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.product.ProductJpaRepository;
import com.lumiere.infrastructure.persistence.jpa.repositories.user.UserJpaRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class CartMapper implements BaseMapper<Cart, CartJpaEntity> {

        private final CartItemMapper cartItemMapper;
        private final UserJpaRepository userJpaRepository;
        private final ProductJpaRepository productJpaRepository;

        public CartMapper(
                        CartItemMapper cartItemMapper,
                        UserJpaRepository userJpaRepository,
                        ProductJpaRepository productJpaRepository) {
                this.cartItemMapper = cartItemMapper;
                this.userJpaRepository = userJpaRepository;
                this.productJpaRepository = productJpaRepository;
        }

        @Override
        public Cart toDomain(CartJpaEntity jpaEntity) {
                return new Cart(
                                Objects.requireNonNull(jpaEntity.getId()),
                                Objects.requireNonNull(jpaEntity.getUserId().getId()),
                                jpaEntity.getCoupon(),
                                jpaEntity.getItems().stream()
                                                .map(cartItemMapper::toDomain)
                                                .collect(Collectors.toList()));
        }

        @Override
        public CartJpaEntity toJpa(Cart domain) {
                UserJpaEntity userJpa = userJpaRepository.getReferenceById(
                                Objects.requireNonNull(domain.getUserId()));

                UUID cartId = Objects.requireNonNull(domain.getId());
                CartJpaEntity cartJpaReference = new CartJpaEntity(
                                cartId,
                                userJpa,
                                domain.getCoupon().orElse(null),
                                null);

                List<CartItemJpaEntity> jpaItems = domain.getItems().stream()
                                .map(domainItem -> {
                                        UUID productId = Objects.requireNonNull(domainItem.getProductId());

                                        ProductJpaEntity productJpa = productJpaRepository.getReferenceById(productId);

                                        return cartItemMapper.toJpa(domainItem, cartJpaReference, productJpa);
                                })
                                .collect(Collectors.toList());

                return new CartJpaEntity(
                                cartId,
                                userJpa,
                                domain.getCoupon().orElse(null),
                                jpaItems);
        }
}