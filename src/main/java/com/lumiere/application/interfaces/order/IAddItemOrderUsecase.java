package com.lumiere.application.interfaces.order;

import com.lumiere.application.dtos.order.command.add.AddItemOrderInput;
import com.lumiere.application.dtos.order.command.add.AddItemOrderOutput;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IAddItemOrderUsecase extends BaseUseCase<AddItemOrderInput, AddItemOrderOutput> {

}
