package com.lumiere.application.interfaces;

import com.lumiere.application.dtos.auth.command.create.CreateUserDTO;
import com.lumiere.application.dtos.auth.response.auth.CreateUserResponse;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface ICreateUserUseCase extends BaseUseCase<CreateUserDTO, CreateUserResponse> {
}
