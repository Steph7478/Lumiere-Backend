package com.lumiere.application.interfaces.auth;

import jakarta.servlet.http.HttpServletRequest;

import com.lumiere.application.dtos.auth.query.logout.LogoutHandler;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface ILogoutUseCase extends BaseUseCase<HttpServletRequest, LogoutHandler> {
}
