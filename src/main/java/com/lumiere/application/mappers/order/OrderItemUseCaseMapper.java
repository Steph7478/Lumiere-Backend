package com.lumiere.application.mappers.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.lumiere.application.dtos.order.command.create.CreateOrderRequestData;
import com.lumiere.domain.readmodels.ProductDetailReadModel;
import com.lumiere.domain.vo.CartItem;
import com.lumiere.domain.vo.OrderItem;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemUseCaseMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "name")
    @Mapping(source = "product.price.amount", target = "unitPrice")
    @Mapping(source = "cartItem.quantity", target = "quantity")
    OrderItem toOrderItem(ProductDetailReadModel product, CartItem cartItem, CreateOrderRequestData reqData);
}