package com.lumiere.application.dtos.admin.command.price;

import java.util.UUID;

import com.lumiere.domain.vo.Money;

import jakarta.validation.constraints.NotNull;

public record UpdatePriceOutput(@NotNull UUID id, @NotNull Money price) {

}
