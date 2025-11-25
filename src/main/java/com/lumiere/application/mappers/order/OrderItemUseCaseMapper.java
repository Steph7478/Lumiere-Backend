package com.lumiere.application.mappers.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.lumiere.application.dtos.order.command.create.CreateOrderRequestData;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.enums.CurrencyEnum.CurrencyType;
import com.lumiere.domain.vo.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderItemUseCaseMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "name")
    @Mapping(source = "requestData.quantity", target = "quantity")
    @Mapping(source = "product.price.amount", target = "unitPrice")
    @Mapping(source = "currency", target = "currency")
    OrderItem toOrderItem(Product product, CreateOrderRequestData requestData, CurrencyType currency);
}