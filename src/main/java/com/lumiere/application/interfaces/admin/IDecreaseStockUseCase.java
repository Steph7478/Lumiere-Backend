package com.lumiere.application.interfaces.admin;

import com.lumiere.application.dtos.admin.command.stock.decrease.DecreaseStockInput;
import com.lumiere.application.dtos.admin.command.stock.decrease.DecreaseStockOutput;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IDecreaseStockUseCase extends BaseUseCase<DecreaseStockInput, DecreaseStockOutput> {

}
