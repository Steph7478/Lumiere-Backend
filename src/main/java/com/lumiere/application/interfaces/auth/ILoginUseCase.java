package com.lumiere.application.interfaces.auth;

import com.lumiere.application.dtos.auth.command.action.LoginDTO;
import com.lumiere.application.dtos.auth.response.auth.LoginOutput;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface ILoginUseCase extends BaseUseCase<LoginDTO, LoginOutput> {
}
