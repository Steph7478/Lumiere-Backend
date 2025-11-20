package com.lumiere.infrastructure.config.serialization;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue; // <--- Importação necessária
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lumiere.domain.enums.CurrencyEnum;
import java.math.BigDecimal;

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
        public StockMixin(int quantity) {
        }

        @JsonValue
        public abstract int getQuantity();
    }
}