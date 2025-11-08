package com.lumiere.application.usecases.auth;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.auth.command.delete.DeleteUserInput;
import com.lumiere.application.dtos.auth.command.delete.output.DeleteUserOutput;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.interfaces.auth.IDeleteUserUseCase;
import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.repositories.AuthRepository;

import jakarta.transaction.Transactional;

@Service
public class DeleteUserUseCase implements IDeleteUserUseCase {
    private final AuthRepository authRepository;

    public DeleteUserUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    @Transactional
    public DeleteUserOutput execute(DeleteUserInput request) {
        Auth auth = authRepository.findById(request.id())
                .orElseThrow(UserNotFoundException::new);

        authRepository.deleteById(auth.getId());
        return new DeleteUserOutput();
    }
}