package com.lumiere.application.interfaces.payment;

import com.lumiere.application.dtos.payment.command.checkout.CreateCheckoutSessionOutput;
import com.lumiere.application.dtos.payment.command.checkout.CreateCheckoutSessionInput;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface ICreateCheckoutSessionUseCase
        extends BaseUseCase<CreateCheckoutSessionInput, CreateCheckoutSessionOutput> {

}
