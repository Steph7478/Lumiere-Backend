package com.lumiere.application.usecases.auth;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.auth.LoginDTO;
import com.lumiere.application.dtos.auth.LoginResponse;
import com.lumiere.application.exceptions.UserNotFound;
import com.lumiere.application.exceptions.InvalidCredentialsException;
import com.lumiere.application.interfaces.ILoginUseCase;
import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.policies.AdminPolicy;
import com.lumiere.domain.repositories.AuthRepository;
import com.lumiere.domain.services.AuthService;
import com.lumiere.infrastructure.security.constants.Roles;
import com.lumiere.infrastructure.http.auth.TokenService;
import com.lumiere.infrastructure.security.constants.Permissions;

@Service
public class LoginUseCase implements ILoginUseCase {

    private final AuthRepository authRepository;
    private final AdminPolicy adminPolicy;

    public LoginUseCase(AuthRepository authRepository, AdminPolicy adminPolicy) {
        this.authRepository = authRepository;
        this.adminPolicy = adminPolicy;
    }

    @Override
    public LoginResponse execute(LoginDTO dto) {
        Auth auth = authRepository.findByEmail(dto.email());
        if (auth == null) {
            throw new UserNotFound();
        }

        boolean isPasswordValid = AuthService.checkPassword(auth, dto.password());
        if (!isPasswordValid) {
            throw new InvalidCredentialsException();
        }

        Roles role = adminPolicy.isAdminInEntity(auth) ? Roles.ADMIN : Roles.USER;

        List<String> roles = List.of(role.name());
        List<String> permissions = role.getPermissions().stream()
                .map(Permissions::getPermission)
                .collect(Collectors.toList());

        try {
            String accessToken = TokenService.generateAccessToken(auth.getId(), roles, permissions);
            String refreshToken = TokenService.generateRefreshToken(auth.getId(), roles, permissions);
            return new LoginResponse(accessToken, refreshToken, auth.getEmail(), role.name());
        } catch (Exception e) {
            throw new RuntimeException("Error to generate Tokens", e);
        }

    }
}
