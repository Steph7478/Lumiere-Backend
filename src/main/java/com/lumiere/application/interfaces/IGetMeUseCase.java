package com.lumiere.application.interfaces;

import com.lumiere.application.dtos.auth.query.GetMeRequest;
import com.lumiere.application.dtos.auth.response.details.GetMeResponse;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IGetMeUseCase extends BaseUseCase<GetMeRequest, GetMeResponse> {
}
