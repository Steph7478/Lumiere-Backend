package com.lumiere.application.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import com.lumiere.application.dtos.auth.response.confirmation.LogoutResponse;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface ILogoutUseCase extends BaseUseCase<HttpServletRequest, LogoutResponse> {
}
