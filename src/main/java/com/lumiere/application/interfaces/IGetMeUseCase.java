package com.lumiere.application.interfaces;

import com.lumiere.application.dtos.command.auth.GetMeRequest;
import com.lumiere.application.dtos.response.auth.GetMeResponse;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IGetMeUseCase extends BaseUseCase<GetMeRequest, GetMeResponse> {
}
