package com.lumiere.application.interfaces.auth;

import com.lumiere.application.dtos.auth.command.create.CreateUserHandler;
import com.lumiere.application.dtos.auth.command.create.CreateUserInput;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface ICreateUserUseCase extends BaseUseCase<CreateUserInput, CreateUserHandler> {
}
