package com.lumiere.application.interfaces;

import com.lumiere.application.dtos.auth.GetMeRequest;
import com.lumiere.application.dtos.auth.GetMeResponse;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IGetMeUseCase extends BaseUseCase<GetMeRequest, GetMeResponse> {
}
