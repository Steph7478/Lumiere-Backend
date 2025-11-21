package com.lumiere.application.interfaces.cart;

import com.lumiere.application.dtos.cart.command.add.AddCartInput;
import com.lumiere.application.dtos.cart.command.add.AddCartOuput;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IAddCartUseCase extends BaseUseCase<AddCartInput, AddCartOuput> {

}
