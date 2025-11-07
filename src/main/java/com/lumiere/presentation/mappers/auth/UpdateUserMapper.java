package com.lumiere.presentation.mappers.auth;

import com.lumiere.application.dtos.auth.updateUser.UpdateUserRequestDTO;
import com.lumiere.application.dtos.auth.updateUser.UpdateUserResponseDTO;
import com.lumiere.presentation.dtos.auth.updateUser.UpdateUserRequest;
import com.lumiere.presentation.dtos.auth.updateUser.UpdateUserResponse;
import com.lumiere.presentation.mappers.base.BaseRequestMapper;
import com.lumiere.presentation.mappers.base.BaseResponseMapper;

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
