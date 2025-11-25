package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.vo.CartItem;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.CartItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper extends BaseMapper<CartItem, CartItemJpaEntity> {

        @Override
        @Mapping(target = "productId", source = "product.id")
        CartItem toDomain(CartItemJpaEntity jpaEntity);

        @Mapping(target = "cart", ignore = true)
        @Mapping(target = "product", source = "productId", qualifiedByName = "getProductEntity")
        @Named("toJpaItems")
        CartItemJpaEntity toJpa(CartItem domain, @Context Map<UUID, ProductJpaEntity> productCache);

        @Named("toJpaItems")
        List<CartItemJpaEntity> toJpa(List<CartItem> domain, @Context Map<UUID, ProductJpaEntity> productCache);

        @Named("getProductEntity")
        default ProductJpaEntity getProductEntity(UUID productId, @Context Map<UUID, ProductJpaEntity> productCache) {
                return productCache.get(productId);
        }
}
