package com.lumiere.application.dtos.order.command.create;

import java.util.List;

import com.lumiere.domain.enums.CurrencyEnum.CurrencyType;

import jakarta.validation.Valid;

public record CreateOrderWrapperRequest(

                @Valid List<CreateOrderRequestData> items, CurrencyType currency) {
}