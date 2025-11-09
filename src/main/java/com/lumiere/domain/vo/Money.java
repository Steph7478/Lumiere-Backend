package com.lumiere.domain.vo;

import java.math.BigDecimal;
import java.util.Objects;

import com.lumiere.domain.enums.CurrencyEnum.*;
import com.lumiere.domain.vo.base.ValueObject;

public class Money extends ValueObject<BigDecimal> {

    private final CurrencyType currency;

    public Money(BigDecimal amount, CurrencyType currency) {
        super(Objects.requireNonNull(amount, "amount cannot be null"));
        this.currency = Objects.requireNonNull(currency, "currency cannot be null");
    }

    @Override
    protected void validate() {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount cannot be negative");
        }
    }

    public BigDecimal getAmount() {
        return value;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currencies must match");
        }
        return new Money(this.value.add(other.value), this.currency);
    }

    public Money subtract(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currencies must match");
        }
        BigDecimal result = this.value.subtract(other.value);
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("result cannot be negative");
        }
        return new Money(result, this.currency);
    }
}
