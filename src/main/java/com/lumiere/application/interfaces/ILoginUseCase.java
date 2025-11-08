package com.lumiere.application.interfaces;

import com.lumiere.application.dtos.auth.command.action.LoginDTO;
import com.lumiere.application.dtos.auth.response.auth.LoginResponse;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface ILoginUseCase extends BaseUseCase<LoginDTO, LoginResponse> {
}
