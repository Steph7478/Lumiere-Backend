package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.vo.CartItem;
import com.lumiere.infrastructure.persistence.jpa.entities.CartItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.CartJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    public CartItem toDomain(CartItemJpaEntity jpaEntity) {
        return new CartItem(
                jpaEntity.getProduct().getId(),
                jpaEntity.getQuantity());
    }

    public CartItemJpaEntity toJpa(
            CartItem domain,
            CartJpaEntity cartJpaEntity,
            ProductJpaEntity productJpaEntity) {

        return new CartItemJpaEntity(
                UUID.randomUUID(),
                cartJpaEntity,
                productJpaEntity,
                domain.getQuantity());
    }

}