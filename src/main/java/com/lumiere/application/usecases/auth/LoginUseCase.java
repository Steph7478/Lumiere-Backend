package com.lumiere.application.usecases.auth;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lumiere.application.dtos.auth.command.action.LoginDTO;
import com.lumiere.application.dtos.auth.response.auth.LoginOutput;
import com.lumiere.application.exceptions.auth.InvalidCredentialsException;
import com.lumiere.application.exceptions.auth.TokenGenerationException;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.interfaces.auth.ILoginUseCase;
import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.repositories.AuthRepository;
import com.lumiere.domain.services.AuthService;
import com.lumiere.infrastructure.http.auth.token.TokenService;
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
    public LoginOutput execute(LoginDTO dto) {
        Auth auth = authRepository.findByEmail(dto.email())
                .orElseThrow(UserNotFoundException::new);

        boolean isPasswordValid = AuthService.checkPassword(auth, dto.password());
        if (!isPasswordValid) {
            throw new InvalidCredentialsException();
        }

        Roles role = auth.isAdmin() ? Roles.ADMIN : Roles.USER;
        Set<String> roles = Set.of(role.name());
        Set<String> permissions = role.getPermissions().stream()
                .map(Permissions::getPermission)
                .collect(Collectors.toSet());

        try {
            String accessToken = TokenService.generateAccessToken(auth.getId(), roles, permissions);
            String refreshToken = TokenService.generateRefreshToken(auth.getId(), roles, permissions);
            return new LoginOutput(accessToken, refreshToken, auth.getName(), role.name());
        } catch (Exception e) {
            throw new TokenGenerationException("Failed to generate tokens", e);
        }
    }
}
