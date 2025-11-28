package com.lumiere.application.interfaces.order;

import com.lumiere.application.dtos.order.command.cancel.CancelOrderInput;
import com.lumiere.application.dtos.order.command.cancel.CancelOrderOutput;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface ICancelOrderUseCase extends BaseUseCase<CancelOrderInput, CancelOrderOutput> {

}
