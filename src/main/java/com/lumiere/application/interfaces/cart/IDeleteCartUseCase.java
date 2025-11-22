package com.lumiere.application.interfaces.cart;

import com.lumiere.application.dtos.cart.command.delete.DeleteCartInput;
import com.lumiere.application.dtos.cart.command.delete.DeleteCartOutput;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IDeleteCartUseCase extends BaseUseCase<DeleteCartInput, DeleteCartOutput> {

}
