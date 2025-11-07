package com.lumiere.application.interfaces;

import com.lumiere.application.dtos.auth.updateUser.UpdateUserInput;
import com.lumiere.application.dtos.auth.updateUser.UpdateUserResponseDTO;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IUpdateUser extends BaseUseCase<UpdateUserInput, UpdateUserResponseDTO> {
}
