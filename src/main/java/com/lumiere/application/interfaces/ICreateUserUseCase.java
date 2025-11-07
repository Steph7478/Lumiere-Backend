package com.lumiere.application.interfaces;

import com.lumiere.application.dtos.auth.createUser.CreateUserDTO;
import com.lumiere.application.dtos.auth.createUser.CreateUserResponse;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface ICreateUserUseCase extends BaseUseCase<CreateUserDTO, CreateUserResponse> {
}
