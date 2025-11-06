package com.lumiere.application.interfaces;

import com.lumiere.application.dtos.auth.UpdateUserInput;
import com.lumiere.application.dtos.auth.UpdateUserResponseDTO;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IUpdateUser extends BaseUseCase<UpdateUserInput, UpdateUserResponseDTO> {
}
