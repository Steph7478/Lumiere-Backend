package com.lumiere.application.interfaces.auth;

import com.lumiere.application.dtos.auth.command.delete.DeleteUserRequest;
import com.lumiere.application.dtos.auth.response.confirmation.DeleteUserResponse;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IDeleteUserUseCase extends BaseUseCase<DeleteUserRequest, DeleteUserResponse> {

}
