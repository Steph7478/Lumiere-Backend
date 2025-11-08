package com.lumiere.application.interfaces;

import com.lumiere.application.dtos.command.auth.CreateUserDTO;
import com.lumiere.application.dtos.response.auth.CreateUserResponse;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface ICreateUserUseCase extends BaseUseCase<CreateUserDTO, CreateUserResponse> {
}
