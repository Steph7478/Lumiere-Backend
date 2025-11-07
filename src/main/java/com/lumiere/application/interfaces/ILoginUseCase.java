package com.lumiere.application.interfaces;

import com.lumiere.application.dtos.auth.login.LoginDTO;
import com.lumiere.application.dtos.auth.login.LoginResponse;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface ILoginUseCase extends BaseUseCase<LoginDTO, LoginResponse> {
}
