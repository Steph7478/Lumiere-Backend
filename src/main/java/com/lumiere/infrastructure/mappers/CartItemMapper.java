package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.vo.CartItem;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.CartItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.CartJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper extends BaseMapper<CartItem, CartItemJpaEntity> {

        CartItem toDomain(CartItemJpaEntity jpaEntity);

        default CartItemJpaEntity toJpa(
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