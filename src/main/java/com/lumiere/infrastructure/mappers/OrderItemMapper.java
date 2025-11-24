package com.lumiere.infrastructure.mappers;

import java.util.Objects;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.lumiere.domain.vo.OrderItem;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.OrderItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.product.ProductJpaRepository;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemMapper extends BaseMapper<OrderItem, OrderItemJpaEntity> {

        @Mapping(target = ".", source = "domain", qualifiedByName = "orderItemToJpa")
        OrderItemJpaEntity toJpa(
                        OrderItem domain,
                        @Context ProductJpaRepository productJpaRepository);

        @Named("orderItemToJpa")
        default OrderItemJpaEntity toJpaWithLookup(
                        OrderItem domain,
                        @Context ProductJpaRepository productJpaRepository) {
                var productJpa = productJpaRepository.getReferenceById(Objects.requireNonNull(domain.getProductId()));

                return new OrderItemJpaEntity(
                                domain.getId(),
                                null, productJpa,
                                domain.getName(), domain.getQuantity(),
                                domain.getUnitPrice());
        }
}
