package com.lumiere.application.mappers.order;

import com.lumiere.domain.entities.Order;
import com.lumiere.domain.readmodels.OrderReadModel;
import com.lumiere.infrastructure.mappers.OrderItemMapper;

import org.mapstruct.EnumMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = { OrderItemMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderReadModelMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "items", source = "items")

    @Mapping(target = "status", source = "status")
    @EnumMapping(nameTransformationStrategy = "case", configuration = "IDENTITY")

    @Mapping(target = "coupon", source = "coupon")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    OrderReadModel toReadModel(Order domain);
}