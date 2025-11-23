package com.lumiere.application.mappers.cart;

import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.readmodels.CartReadModel;
import com.lumiere.infrastructure.mappers.CartItemMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = { CartItemMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartReadModelMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "items", source = "items")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    CartReadModel toReadModel(Cart domain);
}