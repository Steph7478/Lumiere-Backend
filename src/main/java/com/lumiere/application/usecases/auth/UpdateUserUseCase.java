package com.lumiere.application.usecases.auth;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lumiere.application.dtos.auth.command.update.UpdateUserInput;
import com.lumiere.application.dtos.auth.command.update.UpdateUserRequestDTO;
import com.lumiere.application.dtos.auth.response.confirmation.UpdateUserResponseDTO;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.interfaces.auth.IUpdateUser;
import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.repositories.AuthRepository;
import com.lumiere.domain.services.AuthService;
import com.lumiere.shared.annotations.logs.Loggable;

@Service
public class UpdateUserUseCase implements IUpdateUser {
    private final AuthRepository authRepository;

    public UpdateUserUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Loggable
    @Override
    @Transactional
    public UpdateUserResponseDTO execute(UpdateUserInput input) {
        UUID id = input.userId();
        UpdateUserRequestDTO request = input.requestData();

        Auth auth = authRepository.findById(id).orElseThrow(UserNotFoundException::new);

        AuthService.update(
                auth,
                request.name(),
                request.email(),
                request.password());

        authRepository.save(auth);

        return new UpdateUserResponseDTO();
    }
}