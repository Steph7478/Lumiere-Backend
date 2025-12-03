package com.lumiere.application.interfaces.order;

import com.lumiere.application.dtos.order.query.GetOrderInProgressOutput;
import com.lumiere.application.dtos.order.query.GetOrderInput;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IGetOrderInProgressUseCase extends BaseUseCase<GetOrderInput, GetOrderInProgressOutput> {

}
