package com.lumiere.application.interfaces;

import com.lumiere.application.dtos.auth.logout.LogoutResponse;
import com.lumiere.application.interfaces.base.BaseUseCase;

import jakarta.servlet.http.HttpServletRequest;

public interface ILogoutUseCase extends BaseUseCase<HttpServletRequest, LogoutResponse> {
}
