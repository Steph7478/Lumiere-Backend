package com.lumiere.presentation.mappers.auth;

import org.springframework.stereotype.Component;

import com.lumiere.application.dtos.command.auth.UpdateUserRequestDTO;
import com.lumiere.application.dtos.response.auth.UpdateUserResponseDTO;
import com.lumiere.presentation.dtos.command.auth.UpdateUserRequest;
import com.lumiere.presentation.dtos.response.auth.UpdateUserResponse;
import com.lumiere.presentation.mappers.base.BaseRequestMapper;
import com.lumiere.presentation.mappers.base.BaseResponseMapper;

@Component
public class UpdateUserMapper implements BaseRequestMapper<UpdateUserRequest, UpdateUserRequestDTO>,
        BaseResponseMapper<UpdateUserResponseDTO, UpdateUserResponse> {
    private UpdateUserMapper() {
    }

    @Override
    public UpdateUserRequestDTO toApplicationDTO(UpdateUserRequest requestDTO) {
        return new UpdateUserRequestDTO(requestDTO.name(), requestDTO.email(), requestDTO.newPassword());
    }

    @Override
    public UpdateUserResponse toPresentationDTO(UpdateUserResponseDTO responseDTO) {
        return new UpdateUserResponse(responseDTO.message());
    }

}
