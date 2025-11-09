package com.lumiere.domain.vo;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Stream;

import com.lumiere.domain.enums.CurrencyEnum.CurrencyType;
import com.lumiere.domain.vo.base.ValueObject;

public class Money extends ValueObject {

    private final BigDecimal amount;
    private final CurrencyType currency;

    public Money(BigDecimal amount, CurrencyType currency) {
        this.amount = Objects.requireNonNull(amount, "amount cannot be null");
        this.currency = Objects.requireNonNull(currency, "currency cannot be null");
        validate();
    }

    @Override
    protected Stream<Object> getAtomicValues() {
        return Stream.of(amount, currency);
    }

    @Override
    protected void validate() {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount cannot be negative");
        }
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currencies must match");
        }
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public Money subtract(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currencies must match");
        }
        BigDecimal result = this.amount.subtract(other.amount);
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("result cannot be negative");
        }
        return new Money(result, this.currency);
    }
}