package com.lumiere.application.usecases.auth;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lumiere.application.dtos.auth.updateUser.UpdateUserInput;
import com.lumiere.application.dtos.auth.updateUser.UpdateUserRequestDTO;
import com.lumiere.application.dtos.auth.updateUser.UpdateUserResponseDTO;
import com.lumiere.application.exceptions.UserNotFoundException;
import com.lumiere.application.interfaces.IUpdateUser;
import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.domain.services.AuthService;
import com.lumiere.shared.annotations.logs.Loggable;

@Service
public class UpdateUserUseCase implements IUpdateUser {
    private final UserRepository userRepository;

    public UpdateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Loggable
    @Override
    @Transactional
    public UpdateUserResponseDTO execute(UpdateUserInput input) {
        UUID id = input.userId();
        UpdateUserRequestDTO request = input.requestData();

        User user = userRepository.findUserByAuthId(id).orElseThrow(UserNotFoundException::new);
        Auth authToUpdate = user.getAuth();

        AuthService.update(
                authToUpdate,
                request.name(),
                request.email(),
                request.newPassword());

        userRepository.save(user);

        return new UpdateUserResponseDTO();
    }
}