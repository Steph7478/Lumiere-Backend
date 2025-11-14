package com.lumiere.application.dtos.admin.command.price;

import java.math.BigDecimal;
import java.util.Optional;

import com.lumiere.domain.enums.CurrencyEnum.CurrencyType;

public record UpdatePriceRequestData(Optional<BigDecimal> priceAmount, Optional<CurrencyType> currency) {

}
