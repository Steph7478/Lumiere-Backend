package com.lumiere.application.dtos.cart.command.add;

import com.lumiere.domain.readmodels.CartReadModel;

public record AddCartOuput(
                CartReadModel cart) {
}