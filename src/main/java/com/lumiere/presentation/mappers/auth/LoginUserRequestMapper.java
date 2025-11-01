package com.lumiere.presentation.mappers.auth;

import com.lumiere.application.dtos.auth.LoginDTO;
import com.lumiere.presentation.dtos.auth.LoginUserRequestDTO;

public class LoginUserRequestMapper {
    private LoginUserRequestMapper() {
    }

    public static LoginDTO toApplicationDTO(LoginUserRequestDTO request) {
        return new LoginDTO(request.email(), request.password());
    }
}
