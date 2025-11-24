package com.lumiere.infrastructure.mappers;

import com.lumiere.application.exceptions.product.ProductNotFoundException;
import com.lumiere.domain.readmodels.CartItemReadModel;
import com.lumiere.domain.vo.CartItem;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.CartItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.product.ProductJpaRepository;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { ProductMapper.class })
public interface CartItemMapper extends BaseMapper<CartItem, CartItemJpaEntity> {

        @Mapping(target = "productId", source = "product")
        @Mapping(target = "quantity", source = "quantity")
        CartItem toDomain(CartItemJpaEntity jpaEntity);

        @Mapping(target = ".", source = "domain", qualifiedByName = "createdItem")
        CartItemJpaEntity toJpa(CartItem domain, @Context ProductJpaRepository productJpaRepository);

        @Named("createdItem")
        default CartItemJpaEntity createItem(
                        CartItem domainItem,
                        @Context ProductJpaRepository productJpaRepository) {
                ProductJpaEntity productJpa = productJpaRepository
                                .findById(domainItem.getProductId())
                                .orElseThrow(() -> new ProductNotFoundException(domainItem.getProductId()));

                return new CartItemJpaEntity(
                                domainItem.getId(),
                                null,
                                productJpa,
                                domainItem.getQuantity());
        }

        UUID map(ProductJpaEntity productJpa);

        @Mapping(target = "product", source = "productId")
        CartItemReadModel toReadModel(CartItem domain, @Context ProductJpaRepository productJpaRepository);
}
