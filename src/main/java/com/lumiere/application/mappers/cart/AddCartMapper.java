package com.lumiere.application.mappers.cart;

import com.lumiere.application.dtos.cart.command.add.AddCartInput;
import com.lumiere.domain.entities.Cart;
import com.lumiere.infrastructure.mappers.CartItemMapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = { CartItemMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddCartMapper {

    Cart toEntity(AddCartInput input);

    AddCartInput toDTO(Cart entity);
}
