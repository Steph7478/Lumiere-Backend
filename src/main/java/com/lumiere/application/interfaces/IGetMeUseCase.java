package com.lumiere.application.interfaces;

import com.lumiere.application.dtos.auth.getMe.GetMeRequest;
import com.lumiere.application.dtos.auth.getMe.GetMeResponse;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IGetMeUseCase extends BaseUseCase<GetMeRequest, GetMeResponse> {
}
