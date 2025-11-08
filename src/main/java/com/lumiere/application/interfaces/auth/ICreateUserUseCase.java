package com.lumiere.application.interfaces.auth;

import com.lumiere.application.dtos.auth.command.create.CreateUserDTO;
import com.lumiere.application.dtos.auth.response.auth.CreateUserOutput;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface ICreateUserUseCase extends BaseUseCase<CreateUserDTO, CreateUserOutput> {
}
