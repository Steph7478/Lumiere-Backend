package com.lumiere.presentation.mappers.auth;

import com.lumiere.application.dtos.auth.LoginDTO;
import com.lumiere.application.dtos.auth.LoginResponse;
import com.lumiere.presentation.dtos.auth.LoginUserRequestDTO;
import com.lumiere.presentation.dtos.auth.LoginUserResponseDTO;

public class LoginUserMapper {
    private LoginUserMapper() {
    }

    public static LoginDTO toApplicationDTO(LoginUserRequestDTO request) {
        return new LoginDTO(request.email(), request.password());
    }

    public static LoginUserResponseDTO toPresentationDTO(LoginResponse response) {
        return new LoginUserResponseDTO(response.name(), response.role());
    }
}
