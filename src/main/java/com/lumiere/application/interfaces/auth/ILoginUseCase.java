package com.lumiere.application.interfaces.auth;

import com.lumiere.application.dtos.auth.command.login.LoginHandler;
import com.lumiere.application.dtos.auth.command.login.LoginInput;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface ILoginUseCase extends BaseUseCase<LoginInput, LoginHandler> {
}
