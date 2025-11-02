package com.lumiere.presentation.mappers.auth;

import com.lumiere.application.dtos.auth.CreateUserDTO;
import com.lumiere.application.dtos.auth.CreateUserResponse;
import com.lumiere.presentation.dtos.auth.CreateUserRequestDTO;
import com.lumiere.presentation.dtos.auth.CreateUserResponseDTO;

public class CreateUserMapper {

    private CreateUserMapper() {
    }

    public static CreateUserDTO toApplicationDTO(CreateUserRequestDTO request) {
        return new CreateUserDTO(request.email(), request.name(), request.password());
    }

    public static CreateUserResponseDTO toPresentationDTO(CreateUserResponse response) {
        return new CreateUserResponseDTO(response.name(), response.role());
    }
}
