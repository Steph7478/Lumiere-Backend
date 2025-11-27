package com.lumiere.infrastructure.mappers;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.lumiere.domain.vo.OrderItem;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.OrderItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemMapper extends BaseMapper<OrderItem, OrderItemJpaEntity> {

        @Override
        @Mapping(target = "productId", source = "product.id")
        OrderItem toDomain(OrderItemJpaEntity jpaEntity);

        @Mapping(target = "product", source = "productId", qualifiedByName = "toProduct")
        @Mapping(target = "order", ignore = true)
        OrderItemJpaEntity toJpa(OrderItem domain);

        @Named("toProduct")
        default ProductJpaEntity toProduct(UUID productId) {
                return productId == null ? null : new ProductJpaEntity(productId);
        }
}