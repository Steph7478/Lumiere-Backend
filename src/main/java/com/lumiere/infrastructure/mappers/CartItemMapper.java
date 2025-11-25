package com.lumiere.infrastructure.mappers;

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

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper extends BaseMapper<CartItem, CartItemJpaEntity> {

        @Named("toDomainWithRepo")
        @Mapping(target = "productId", source = "product.id")
        CartItem toDomain(CartItemJpaEntity jpaEntity);

        @Mapping(target = "product", source = "item.productId", qualifiedByName = "loadProductRef")
        @Mapping(target = "quantity", source = "item.quantity")
        CartItemJpaEntity toJpa(CartItem domain, @Context ProductJpaRepository productJpaRepository);

        @Named("loadProductRef")
        default ProductJpaEntity loadProductReference(UUID productId,
                        @Context ProductJpaRepository productJpaRepository) {
                if (productId == null)
                        return null;
                return productJpaRepository.getReferenceById(productId);
        }

        default UUID map(ProductJpaEntity productJpa) {
                return productJpa != null ? productJpa.getId() : null;
        }
}