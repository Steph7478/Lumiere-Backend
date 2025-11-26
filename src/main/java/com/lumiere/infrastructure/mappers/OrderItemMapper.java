package com.lumiere.infrastructure.mappers;

import java.util.Map;
import java.util.UUID;

import org.mapstruct.Context;
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

        @Mapping(target = "productId", expression = "java(jpaEntity.getProduct() != null ? jpaEntity.getProduct().getId() : null)")
        OrderItem toDomain(OrderItemJpaEntity jpaEntity);

        @Mapping(target = "product", source = "productId", qualifiedByName = "getProductEntity")
        @Mapping(target = "quantity", expression = "java(domain.getQuantity())")
        @Mapping(target = "unitPrice", expression = "java(domain.getUnitPrice())")
        @Mapping(target = "order", source = "domain", ignore = true)
        OrderItemJpaEntity toJpa(OrderItem domain, @Context Map<UUID, ProductJpaEntity> productCache);

        @Named("getProductEntity")
        default ProductJpaEntity getProductEntity(UUID productId,
                        @Context Map<UUID, ProductJpaEntity> productCache) {

                return productCache.get(productId);
        }
}
