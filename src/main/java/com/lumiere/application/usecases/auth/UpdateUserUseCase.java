package com.lumiere.application.usecases.auth;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lumiere.application.dtos.auth.command.update.UpdateUserInput;
import com.lumiere.application.dtos.auth.command.update.UpdateUserOutput;
import com.lumiere.application.dtos.auth.command.update.UpdateUserRequestData;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.interfaces.auth.IUpdateUser;
import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.repositories.AuthRepository;
import com.lumiere.domain.services.AuthService;

@Service
public class UpdateUserUseCase implements IUpdateUser {
    private final AuthRepository authRepository;

    public UpdateUserUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    @Transactional
    public UpdateUserOutput execute(UpdateUserInput input) {
        UUID id = input.userId();
        UpdateUserRequestData request = input.requestData();

        Auth auth = authRepository.findById(id).orElseThrow(UserNotFoundException::new);

        AuthService.update(
                auth,
                request.name(),
                request.email(),
                request.password());

        authRepository.update(auth);

        return new UpdateUserOutput();
    }
}