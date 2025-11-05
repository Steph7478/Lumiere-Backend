package com.lumiere.application.usecases.auth;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lumiere.application.dtos.auth.LoginDTO;
import com.lumiere.application.dtos.auth.LoginResponse;
import com.lumiere.application.exceptions.InvalidCredentialsException;
import com.lumiere.application.exceptions.TokenGenerationException;
import com.lumiere.application.exceptions.UserNotFoundException;
import com.lumiere.application.interfaces.ILoginUseCase;
import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.repositories.AuthRepository;
import com.lumiere.domain.services.AuthService;
import com.lumiere.infrastructure.http.auth.TokenService;
import com.lumiere.shared.annotations.logs.Loggable;
import com.lumiere.shared.constants.Permissions;
import com.lumiere.shared.constants.Roles;

@Service
public class LoginUseCase implements ILoginUseCase {

    private final AuthRepository authRepository;

    public LoginUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    @Loggable
    @Transactional(readOnly = true)
    public LoginResponse execute(LoginDTO dto) {
        Auth auth = authRepository.findByEmail(dto.email())
                .orElseThrow(UserNotFoundException::new);

        boolean isPasswordValid = AuthService.checkPassword(auth, dto.password());
        if (!isPasswordValid) {
            throw new InvalidCredentialsException();
        }

        Roles role = auth.isAdmin() ? Roles.ADMIN : Roles.USER;
        List<String> roles = List.of(role.name());
        List<String> permissions = role.getPermissions().stream()
                .map(Permissions::getPermission)
                .collect(Collectors.toList());

        try {
            String accessToken = TokenService.generateAccessToken(auth.getId(), roles, permissions);
            String refreshToken = TokenService.generateRefreshToken(auth.getId(), roles, permissions);
            return new LoginResponse(accessToken, refreshToken, auth.getName(), role.name());
        } catch (Exception e) {
            throw new TokenGenerationException("Failed to generate tokens", e);
        }
    }
}
