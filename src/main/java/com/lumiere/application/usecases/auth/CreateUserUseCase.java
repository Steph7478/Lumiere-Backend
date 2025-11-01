package com.lumiere.application.usecases.auth;

import com.lumiere.application.dtos.auth.CreateUserDTO;
import com.lumiere.application.dtos.auth.CreateUserResponse;
import com.lumiere.application.exceptions.EmailAlreadyExistsException;
import com.lumiere.application.interfaces.ICreateUserUseCase;
import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.services.AuthService;
import com.lumiere.domain.services.UserService;
import com.lumiere.domain.repositories.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateUserUseCase implements ICreateUserUseCase {

    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CreateUserResponse execute(CreateUserDTO dto) {

        if (userRepository.findByAuthEmail(dto.email()) != null) {
            throw new EmailAlreadyExistsException(dto.email());
        }

        Auth auth = AuthService.createAuth(dto.name(), dto.email(), dto.password(), false);
        User user = UserService.createUser(auth);

        userRepository.save(user);

        return new CreateUserResponse();
    }

}
