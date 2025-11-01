package com.lumiere.presentation.controllers;

import com.lumiere.presentation.dtos.auth.CreateUserRequestDTO;
import com.lumiere.presentation.mappers.auth.CreateUserRequestMapper;
import com.lumiere.application.dtos.auth.CreateUserDTO;
import com.lumiere.application.dtos.auth.CreateUserResponse;
import com.lumiere.application.interfaces.ICreateUserUseCase;
import com.lumiere.infrastructure.http.cookies.CookieFactory;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ICreateUserUseCase createUserUseCase;

    public AuthController(ICreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponse> registerUser(
            @Valid @RequestBody CreateUserRequestDTO requestDTO,
            HttpServletResponse response) {

        CreateUserDTO appDTO = CreateUserRequestMapper.toApplicationDTO(requestDTO);

        CreateUserResponse responseDTO = createUserUseCase.execute(appDTO);

        response.addCookie(CookieFactory.createAccessTokenCookie(responseDTO.accessToken()));
        response.addCookie(CookieFactory.createRefreshTokenCookie(responseDTO.refreshToken()));

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
