package com.lumiere.presentation.mappers.auth;

import com.lumiere.application.dtos.auth.CreateUserDTO;
import com.lumiere.presentation.dtos.auth.CreateUserRequestDTO;

public class CreateUserRequestMapper {

    private CreateUserRequestMapper() {
    }

    public static CreateUserDTO toApplicationDTO(CreateUserRequestDTO request) {
        return new CreateUserDTO(request.email(), request.name(), request.password());
    }
}
