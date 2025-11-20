package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.vo.CartItem;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.CartItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.CartJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper extends BaseMapper<CartItem, CartItemJpaEntity> {

        @Mapping(target = "productId", source = "product.id")
        @Mapping(target = "quantity", source = "quantity")
        CartItem toDomain(CartItemJpaEntity jpaEntity);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "cart", source = "cartJpaEntity")
        @Mapping(target = "product", source = "productJpaEntity")
        @Mapping(target = "quantity", source = "domain.quantity")
        CartItemJpaEntity toJpa(
                        CartItem domain,
                        CartJpaEntity cartJpaEntity,
                        ProductJpaEntity productJpaEntity);

        default CartItemJpaEntity createJpaEntity(
                        CartJpaEntity cart,
                        ProductJpaEntity product,
                        Integer quantity) {

                return new CartItemJpaEntity(
                                UUID.randomUUID(),
                                cart,
                                product,
                                quantity);
        }
}