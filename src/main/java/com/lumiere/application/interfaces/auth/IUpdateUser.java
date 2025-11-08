package com.lumiere.application.interfaces.auth;

import com.lumiere.application.dtos.auth.command.update.UpdateUserInput;
import com.lumiere.application.dtos.auth.response.confirmation.UpdateUserResponseDTO;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IUpdateUser extends BaseUseCase<UpdateUserInput, UpdateUserResponseDTO> {
}
