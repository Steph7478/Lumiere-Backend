package com.lumiere.application.interfaces.admin;

import com.lumiere.application.dtos.admin.command.stock.increase.IncreaseStockInput;
import com.lumiere.application.dtos.admin.command.stock.increase.IncreaseStockOutput;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IIncreaseStockUseCase extends BaseUseCase<IncreaseStockInput, IncreaseStockOutput> {

}
