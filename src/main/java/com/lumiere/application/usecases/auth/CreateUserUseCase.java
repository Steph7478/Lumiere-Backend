package com.lumiere.application.usecases.auth;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lumiere.application.dtos.auth.CreateUserDTO;
import com.lumiere.application.dtos.auth.CreateUserResponse;
import com.lumiere.application.exceptions.EmailAlreadyExistsException;
import com.lumiere.application.interfaces.ICreateUserUseCase;
import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.policies.AdminPolicy;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.domain.services.AuthService;
import com.lumiere.domain.services.UserService;
import com.lumiere.infrastructure.security.constants.Roles;
import com.lumiere.infrastructure.http.auth.TokenService;
import com.lumiere.infrastructure.security.constants.Permissions;

@Service
public class CreateUserUseCase implements ICreateUserUseCase {

    private final UserRepository userRepository;
    private final AdminPolicy adminPolicy;

    public CreateUserUseCase(UserRepository userRepository, AdminPolicy adminPolicy) {
        this.userRepository = userRepository;
        this.adminPolicy = adminPolicy;
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

        Roles role = adminPolicy.isAdminInEntity(auth) ? Roles.ADMIN : Roles.USER;

        List<String> roles = List.of(role.name());
        List<String> permissions = role.getPermissions()
                .stream()
                .map(Permissions::getPermission)
                .collect(Collectors.toList());

        try {
            String accessToken = TokenService.generateAccessToken(auth.getId(), roles, permissions);
            String refreshToken = TokenService.generateRefreshToken(auth.getId(), roles, permissions);

            return new CreateUserResponse(auth.getEmail(), accessToken, refreshToken, role.name());
        } catch (Exception e) {
            throw new RuntimeException("Error generating tokens", e);
        }
    }
}
