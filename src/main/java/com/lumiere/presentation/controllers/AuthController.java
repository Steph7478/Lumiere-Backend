package com.lumiere.presentation.controllers;

import com.lumiere.presentation.dtos.auth.CreateUserRequestDTO;
import com.lumiere.presentation.dtos.auth.CreateUserResponseDTO;
import com.lumiere.presentation.dtos.auth.LoginUserRequestDTO;
import com.lumiere.presentation.dtos.auth.LoginUserResponseDTO;
import com.lumiere.presentation.mappers.auth.CreateUserMapper;
import com.lumiere.presentation.mappers.auth.LoginUserMapper;
import com.lumiere.presentation.routes.Routes;
import com.lumiere.shared.annotations.Loggable;
import com.lumiere.application.dtos.auth.CreateUserDTO;
import com.lumiere.application.dtos.auth.CreateUserResponse;
import com.lumiere.application.dtos.auth.LoginDTO;
import com.lumiere.application.dtos.auth.LoginResponse;
import com.lumiere.application.interfaces.ICreateUserUseCase;
import com.lumiere.application.interfaces.ILoginUseCase;
import com.lumiere.infrastructure.http.cookies.CookieFactory;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routes.Auth.BASE)
public class AuthController {

    private final ICreateUserUseCase createUserUseCase;
    private final ILoginUseCase loginUseCase;

    public AuthController(ICreateUserUseCase createUserUseCase, ILoginUseCase loginUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.loginUseCase = loginUseCase;
    }

    @Loggable
    @PostMapping(Routes.Auth.REGISTER)
    public ResponseEntity<CreateUserResponseDTO> registerUser(
            @Valid @RequestBody CreateUserRequestDTO requestDTO,
            HttpServletResponse response) {

        CreateUserDTO appDTO = CreateUserMapper.toApplicationDTO(requestDTO);

        CreateUserResponse responseDTO = createUserUseCase.execute(appDTO);

        response.addCookie(CookieFactory.createAccessTokenCookie(responseDTO.accessToken()));
        response.addCookie(CookieFactory.createRefreshTokenCookie(responseDTO.refreshToken()));

        CreateUserResponseDTO body = CreateUserMapper.toPresentationDTO(responseDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @Loggable
    @PostMapping(Routes.Auth.LOGIN)
    public ResponseEntity<LoginUserResponseDTO> loginUser(
            @Valid @RequestBody LoginUserRequestDTO requestDTO, HttpServletResponse response) {

        LoginDTO appDTO = LoginUserMapper.toApplicationDTO(requestDTO);
        LoginResponse responseDTO = loginUseCase.execute(appDTO);

        response.addCookie(CookieFactory.createAccessTokenCookie(responseDTO.accessToken()));
        response.addCookie(CookieFactory.createRefreshTokenCookie(responseDTO.refreshToken()));

        LoginUserResponseDTO body = LoginUserMapper.toPresentationDTO(responseDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(body);
    }
}
