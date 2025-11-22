package com.lumiere.infrastructure.mappers;

import com.lumiere.application.exceptions.product.ProductNotFoundException;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.readmodels.CartItemReadModel;
import com.lumiere.domain.vo.CartItem;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.CartItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.product.ProductJpaRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { ProductMapper.class })
public abstract class CartItemMapper implements BaseMapper<CartItem, CartItemJpaEntity> {

        @Autowired
        protected ProductJpaRepository productJpaRepository;

        @Autowired
        protected ProductMapper productMapper;

        @Mapping(target = "productId", source = "product")
        @Mapping(target = "quantity", source = "quantity")
        public abstract CartItem toDomain(CartItemJpaEntity jpaEntity);

        @Mapping(target = "product", source = "productId")
        @Mapping(target = "quantity", source = "quantity")
        public abstract CartItemReadModel toReadModel(CartItem domain);

        protected CartItemReadModel fullProduct(Product product, Integer quantity) {
                return new CartItemReadModel(product, quantity);
        }

        public CartItemJpaEntity toJpa(CartItem domain) {
                return createItem(domain);
        }

        @Named("createdItem")
        protected CartItemJpaEntity createItem(CartItem domainItem) {

                ProductJpaEntity productJpa = productJpaRepository
                                .findById(domainItem.getProductId())
                                .orElseThrow(() -> new ProductNotFoundException(domainItem.getProductId()));

                return new CartItemJpaEntity(domainItem.getId(), null, productJpa, domainItem.getQuantity());
        }

        protected Product map(UUID productId) {
                ProductJpaEntity productJpa = productJpaRepository
                                .findById(productId)
                                .orElseThrow(() -> new ProductNotFoundException(productId));
                return productMapper.toDomain(productJpa);
        }

        public UUID map(ProductJpaEntity productJpa) {
                return productJpa != null ? productJpa.getId() : null;
        }
}