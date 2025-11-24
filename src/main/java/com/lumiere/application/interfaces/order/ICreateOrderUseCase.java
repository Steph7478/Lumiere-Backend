package com.lumiere.application.interfaces.order;

import com.lumiere.application.dtos.order.command.create.CreateOrderInput;
import com.lumiere.application.dtos.order.command.create.CreateOrderOutput;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface ICreateOrderUseCase extends BaseUseCase<CreateOrderInput, CreateOrderOutput> {

}
