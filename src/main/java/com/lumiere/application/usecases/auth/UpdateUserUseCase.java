package com.lumiere.application.usecases.auth;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.auth.updateUser.UpdateUserInput;
import com.lumiere.application.dtos.auth.updateUser.UpdateUserRequestDTO;
import com.lumiere.application.dtos.auth.updateUser.UpdateUserResponseDTO;
import com.lumiere.application.exceptions.UserNotFoundException;
import com.lumiere.application.interfaces.IUpdateUser;
import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.repositories.AuthRepository;
import com.lumiere.domain.services.AuthService;
import com.lumiere.shared.annotations.logs.Loggable;

import jakarta.transaction.Transactional;

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
        Auth newAuth = AuthService.update(auth, request.name(), request.email(), request.newPassword());

        authRepository.save(newAuth);

        return new UpdateUserResponseDTO();
    }
}
