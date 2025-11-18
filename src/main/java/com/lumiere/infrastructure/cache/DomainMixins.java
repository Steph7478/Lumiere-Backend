package com.lumiere.infrastructure.cache;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lumiere.domain.entities.Rating;
import com.lumiere.domain.enums.CurrencyEnum;
import com.lumiere.domain.vo.Money;
import com.lumiere.domain.vo.Stock;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public abstract class DomainMixins {

    public static abstract class MoneyMixin {
        @JsonCreator
        public MoneyMixin(
                @JsonProperty("amount") BigDecimal amount,
                @JsonProperty("currency") CurrencyEnum.CurrencyType currency) {
        }
    }

    public static abstract class StockMixin {
        @JsonCreator
        public StockMixin(
                @JsonProperty("quantity") int quantity) {
        }
    }
}