package com.lumiere.application.interfaces.order;

import com.lumiere.application.dtos.order.query.GetOrderInput;
import com.lumiere.application.dtos.order.query.GetOrdersOutput;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IGetOrdersUseCase extends BaseUseCase<GetOrderInput, GetOrdersOutput> {

}
