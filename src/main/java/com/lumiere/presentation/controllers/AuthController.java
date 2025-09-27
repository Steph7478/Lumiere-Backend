package com.lumiere.presentation.controllers;

import com.lumiere.presentation.dtos.auth.CreateUserRequestDTO;
import com.lumiere.presentation.exceptions.TokenGenerationException;
import com.lumiere.presentation.mappers.auth.CreateUserRequestMapper;
import com.lumiere.application.dtos.auth.CreateUserDTO;
import com.lumiere.application.dtos.auth.CreateUserResponse;
import com.lumiere.application.usecases.auth.CreateUserUseCase;
import com.lumiere.infrastructure.auth.TokenService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CreateUserUseCase createUserUseCase;

    public AuthController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponse> registerUser(
            @Valid @RequestBody CreateUserRequestDTO requestDTO,
            HttpServletResponse response) {

        CreateUserDTO appDTO = CreateUserRequestMapper.toApplicationDTO(requestDTO);
        CreateUserResponse responseDTO = createUserUseCase.execute(appDTO);

        try {
            List<String> ROLES = List.of("USER");
            List<String> PERMISSIONS = List.of("READ_ONLY");

            String accessToken = TokenService.generateAccessToken(UUID.randomUUID(), ROLES, PERMISSIONS);
            String refreshToken = TokenService.generateRefreshToken(UUID.randomUUID(), ROLES, PERMISSIONS);

            Cookie accessCookie = new Cookie("access_token", accessToken);
            accessCookie.setHttpOnly(true);
            accessCookie.setSecure(true);
            accessCookie.setPath("/");
            accessCookie.setMaxAge(15 * 60);
            response.addCookie(accessCookie);

            Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
            refreshCookie.setHttpOnly(true);
            refreshCookie.setSecure(true);
            refreshCookie.setPath("/");
            refreshCookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(refreshCookie);

        } catch (Exception e) {
            throw new TokenGenerationException(e);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
