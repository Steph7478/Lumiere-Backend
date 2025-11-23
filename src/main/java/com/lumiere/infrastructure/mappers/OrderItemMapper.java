package com.lumiere.infrastructure.mappers;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.lumiere.application.exceptions.product.ProductNotFoundException;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.readmodels.OrderItemReadModel;
import com.lumiere.domain.vo.CartItem;
import com.lumiere.domain.vo.OrderItem;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.OrderItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.product.ProductJpaRepository;

@Mapper(componentModel = "spring", uses = { ProductMapper.class })
public abstract class OrderItemMapper implements BaseMapper<OrderItem, OrderItemJpaEntity> {

    @Autowired
    protected ProductJpaRepository productJpaRepository;

    @Autowired
    protected ProductMapper productMapper;

    @Mapping(target = "productId", source = "product")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "unitPrice", source = "unitPrice")
    public abstract OrderItem toDomain(OrderItemJpaEntity jpaEntity);

    public OrderItemJpaEntity toJpa(OrderItem domain) {
        return createItem(domain);
    }

    @Named("createdItem")
    protected OrderItemJpaEntity createItem(OrderItem domain) {

        ProductJpaEntity productJpa = productJpaRepository.findById(domain.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(domain.getProductId()));

        return new OrderItemJpaEntity(domain.getId(), null, productJpa, domain.getName(), domain.getQuantity(),
                domain.getUnitPrice());
    }

    public UUID map(ProductJpaEntity productJpa) {
        return productJpa != null ? productJpa.getId() : null;
    }

    @Mapping(target = "product", source = "productId")
    public abstract OrderItemReadModel toReadModel(CartItem domain);

    protected Product map(UUID productId) {
        ProductJpaEntity productJpa = productJpaRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        return productMapper.toDomain(productJpa);
    }
}
