package com.lumiere.infrastructure.mappers;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.lumiere.domain.readmodels.CartItemReadModel;
import com.lumiere.domain.vo.CartItem;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.CartItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper extends BaseMapper<CartItem, CartItemJpaEntity> {

    @Override
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "quantity", source = "quantity")
    CartItem toDomain(CartItemJpaEntity jpaEntity);

    @Mapping(target = "product", source = "productId", qualifiedByName = "toProduct")
    @Mapping(target = "cart", ignore = true)
    CartItemJpaEntity toJpa(CartItem domain);

    @Named("toProduct")
    default ProductJpaEntity toProduct(UUID productId) {
        return productId == null ? null : new ProductJpaEntity(productId);
    }
}