package com.lumiere.application.interfaces.products;

import com.lumiere.application.dtos.product.command.add.AddProductInput;
import com.lumiere.application.dtos.product.command.add.output.AddProductOutput;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IAddProductUseCase extends BaseUseCase<AddProductInput, AddProductOutput> {

}
