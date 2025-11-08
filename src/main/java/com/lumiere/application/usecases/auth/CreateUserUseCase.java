package com.lumiere.application.usecases.auth;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lumiere.application.dtos.command.auth.CreateUserDTO;
import com.lumiere.application.dtos.response.auth.CreateUserResponse;
import com.lumiere.application.exceptions.EmailAlreadyExistsException;
import com.lumiere.application.exceptions.TokenGenerationException;
import com.lumiere.application.interfaces.ICreateUserUseCase;
import com.lumiere.application.mappers.CreateUserUseCaseMapper;
import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.domain.services.UserService;
import com.lumiere.infrastructure.http.auth.token.TokenService;
import com.lumiere.shared.annotations.logs.Loggable;
import com.lumiere.shared.constants.Permissions;
import com.lumiere.shared.constants.Roles;

@Service
public class CreateUserUseCase implements ICreateUserUseCase {

    private final UserRepository userRepository;
    private final CreateUserUseCaseMapper authMapper;

    public CreateUserUseCase(UserRepository userRepository, CreateUserUseCaseMapper authMapper) {
        this.userRepository = userRepository;
        this.authMapper = authMapper;
    }

    @Override
    @Transactional
    @Loggable
    public CreateUserResponse execute(CreateUserDTO dto) {

        if (userRepository.findByAuthEmail(dto.email()).isPresent()) {
            throw new EmailAlreadyExistsException(dto.email());
        }

        Auth auth = authMapper.toEntity(dto);
        User user = UserService.createUser(auth);
        userRepository.save(user);

        Roles role = auth.isAdmin() ? Roles.ADMIN : Roles.USER;
        Set<String> roles = Set.of(role.name());
        Set<String> permissions = role.getPermissions()
                .stream()
                .map(Permissions::getPermission)
                .collect(Collectors.toSet());

        try {
            String accessToken = TokenService.generateAccessToken(auth.getId(), roles, permissions);
            String refreshToken = TokenService.generateRefreshToken(auth.getId(), roles, permissions);

            return new CreateUserResponse(auth.getName(), accessToken, refreshToken, role.name());
        } catch (Exception e) {
            throw new TokenGenerationException("Failed to generate tokens", e);
        }
    }
}
