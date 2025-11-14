package com.lumiere.application.dtos.admin.command.price;

import java.util.UUID;

import com.lumiere.domain.vo.Money;

public record UpdatePriceOutput(UUID id, Money price) {

}
