package com.lumiere.application.interfaces;

import com.lumiere.application.dtos.command.auth.LoginDTO;
import com.lumiere.application.dtos.response.auth.LoginResponse;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface ILoginUseCase extends BaseUseCase<LoginDTO, LoginResponse> {
}
