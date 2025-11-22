package com.lumiere.application.interfaces.cart;

import com.lumiere.application.dtos.cart.command.remove.RemoveCartInput;
import com.lumiere.application.dtos.cart.command.remove.RemoveCartOutput;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IRemoveCartUseCase extends BaseUseCase<RemoveCartInput, RemoveCartOutput> {

}
