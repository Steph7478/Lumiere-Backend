package com.lumiere.application.interfaces.order;

import com.lumiere.application.dtos.order.command.remove.RemoveItemOrderInput;
import com.lumiere.application.dtos.order.command.remove.RemoveItemOutput;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IRemoveItemOrderUseCase extends BaseUseCase<RemoveItemOrderInput, RemoveItemOutput> {

}
