package com.lumiere.application.usecases.auth;

import com.lumiere.application.dtos.auth.AuthResponseDTO;
import com.lumiere.application.dtos.auth.CreateUserDTO;
import com.lumiere.application.mappers.AuthMapper;
import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.repositories.AuthRepository;
import com.lumiere.domain.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateUserUseCase {

    private final AuthRepository authRepository;

    public CreateUserUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Transactional
    public AuthResponseDTO execute(CreateUserDTO dto) {
        Auth auth = AuthMapper.toEntity(dto);

        Auth savedAuth = authRepository.save(auth);

        User user = UserService.createUser(savedAuth);

        return new AuthResponseDTO(
                user.getId().toString(),
                user.getName());
    }
}
