package com.lumiere.application.mappers.cart;

import com.lumiere.application.dtos.cart.command.add.AddCartInput;
import com.lumiere.application.dtos.cart.command.remove.RemoveMultipleItemsRequestData;
import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.services.CartService;
import com.lumiere.infrastructure.mappers.CartItemMapper;

import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = { CartItemMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RemoveCartReadModel {

    Cart toEntity(AddCartInput toEntity);

    @ObjectFactory
    default Cart removeProduct(Cart currentCart, RemoveMultipleItemsRequestData reqData) {
        return CartService.removeProduct(
                currentCart,
                reqData.items());
    }
}