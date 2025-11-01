package com.lumiere.application.interfaces;

import com.lumiere.application.dtos.auth.CreateUserDTO;
import com.lumiere.application.dtos.auth.CreateUserResponse;

public interface ICreateUserUseCase {
    CreateUserResponse execute(CreateUserDTO dto);
}
