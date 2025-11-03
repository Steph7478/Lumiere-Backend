package com.lumiere.presentation.mappers.auth;

import org.springframework.stereotype.Component;

import com.lumiere.application.dtos.auth.LoginDTO;
import com.lumiere.application.dtos.auth.LoginResponse;
import com.lumiere.presentation.dtos.auth.LoginUserRequestDTO;
import com.lumiere.presentation.dtos.auth.LoginUserResponseDTO;
import com.lumiere.presentation.mappers.base.BaseRequestMapper;
import com.lumiere.presentation.mappers.base.BaseResponseMapper;

@Component
public class LoginUserMapper implements BaseRequestMapper<LoginUserRequestDTO, LoginDTO>,
        BaseResponseMapper<LoginResponse, LoginUserResponseDTO> {

    private LoginUserMapper() {
    }

    @Override
    public LoginDTO toApplicationDTO(LoginUserRequestDTO request) {
        return new LoginDTO(request.email(), request.password());
    }

    @Override
    public LoginUserResponseDTO toPresentationDTO(LoginResponse response) {
        return new LoginUserResponseDTO(response.name(), response.role());
    }
}
