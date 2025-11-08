package com.lumiere.presentation.mappers.auth;

import com.lumiere.application.dtos.auth.command.create.CreateUserDTO;
import com.lumiere.application.dtos.auth.response.auth.CreateUserResponse;
import com.lumiere.presentation.dtos.command.auth.CreateUserRequestDTO;
import com.lumiere.presentation.dtos.response.auth.CreateUserResponseDTO;
import com.lumiere.presentation.mappers.base.BaseRequestMapper;
import com.lumiere.presentation.mappers.base.BaseResponseMapper;

import org.springframework.stereotype.Component;

@Component
public class CreateUserMapper implements BaseRequestMapper<CreateUserRequestDTO, CreateUserDTO>,
        BaseResponseMapper<CreateUserResponse, CreateUserResponseDTO> {
    private CreateUserMapper() {
    }

    @Override
    public CreateUserDTO toApplicationDTO(CreateUserRequestDTO request) {
        return new CreateUserDTO(request.email(), request.name(), request.password());
    }

    @Override
    public CreateUserResponseDTO toPresentationDTO(CreateUserResponse response) {
        return new CreateUserResponseDTO(response.name(), response.role());
    }
}
