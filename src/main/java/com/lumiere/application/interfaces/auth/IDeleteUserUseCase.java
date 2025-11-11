package com.lumiere.application.interfaces.auth;

import com.lumiere.application.dtos.auth.command.delete.DeleteUserInput;
import com.lumiere.application.dtos.auth.command.delete.DeleteUserOutput;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IDeleteUserUseCase extends BaseUseCase<DeleteUserInput, DeleteUserOutput> {

}
