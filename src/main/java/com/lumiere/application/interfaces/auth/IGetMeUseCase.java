package com.lumiere.application.interfaces.auth;

import com.lumiere.application.dtos.auth.query.me.GetMeInput;
import com.lumiere.application.dtos.auth.query.me.GetMeOutput;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IGetMeUseCase extends BaseUseCase<GetMeInput, GetMeOutput> {
}
