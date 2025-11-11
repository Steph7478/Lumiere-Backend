package com.lumiere.application.interfaces.auth;

import com.lumiere.application.dtos.auth.command.update.UpdateUserInput;
import com.lumiere.application.dtos.auth.command.update.UpdateUserOutput;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IUpdateUser extends BaseUseCase<UpdateUserInput, UpdateUserOutput> {
}
