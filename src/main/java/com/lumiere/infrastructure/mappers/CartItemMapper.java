package com.lumiere.infrastructure.mappers;

import com.lumiere.application.exceptions.product.ProductNotFoundException;
import com.lumiere.domain.entities.Product;
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

@Mapper(componentModel = "spring", uses = { ProductMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper extends BaseMapper<CartItem, CartItemJpaEntity> {

        @Named("toDomainWithRepo")
        CartItem toDomain(CartItemJpaEntity jpaEntity, @Context ProductJpaRepository productRepo);

        default CartItemJpaEntity toJpa(CartItem domain, @Context ProductJpaRepository productRepo,
                        @Context ProductMapper productMapper) {
                ProductJpaEntity productJpa = productRepo
                                .findById(domain.getProductId())
                                .orElseThrow(() -> new ProductNotFoundException(domain.getProductId()));
                return new CartItemJpaEntity(domain.getId(), null, productJpa, domain.getQuantity());
        }

        @Named("productIdToProduct")
        default Product map(UUID productId, @Context ProductJpaRepository productRepo,
                        @Context ProductMapper productMapper) {
                ProductJpaEntity productJpa = productRepo
                                .findById(productId)
                                .orElseThrow(() -> new ProductNotFoundException(productId));
                return productMapper.toDomain(productJpa);
        }

        @Mapping(target = "product", source = "productId", qualifiedByName = "productIdToProduct")
        CartItemReadModel toReadModel(CartItem domain, @Context ProductJpaRepository productRepo,
                        @Context ProductMapper productMapper);

        default UUID map(ProductJpaEntity productJpa) {
                return productJpa != null ? productJpa.getId() : null;
        }
}
