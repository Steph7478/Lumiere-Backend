package com.lumiere.infrastructure.mappers;

import com.lumiere.application.exceptions.product.ProductNotFoundException;
import com.lumiere.domain.vo.CartItem;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.CartItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.product.ProductJpaRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper extends BaseMapper<CartItem, CartItemJpaEntity> {

        @Mapping(target = "productId", source = "product")
        @Mapping(target = "quantity", source = "quantity")
        CartItem toDomain(CartItemJpaEntity jpaEntity);

        default CartItemJpaEntity toJpa(CartItem domain,
                        ProductJpaRepository productJpaRepository) {
                return createItem(domain, productJpaRepository);
        }

        @Named("createdItem")
        default CartItemJpaEntity createItem(CartItem domainItem,
                        ProductJpaRepository productJpaRepository) {

                ProductJpaEntity productJpa = productJpaRepository
                                .findById(domainItem.getProductId())
                                .orElseThrow(() -> new ProductNotFoundException(domainItem.getProductId()));

                return new CartItemJpaEntity(domainItem.getId(), null, productJpa, domainItem.getQuantity());
        }

        default UUID map(ProductJpaEntity productJpa) {
                return productJpa != null ? productJpa.getId() : null;
        }
}