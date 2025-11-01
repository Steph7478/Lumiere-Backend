package com.lumiere.application.interfaces;

import com.lumiere.application.dtos.auth.LoginDTO;
import com.lumiere.application.dtos.auth.LoginResponse;

public interface ILoginUseCase {
    LoginResponse execute(LoginDTO request);
}
