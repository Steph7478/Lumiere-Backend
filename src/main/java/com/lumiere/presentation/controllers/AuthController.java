package com.lumiere.presentation.controllers;

import com.lumiere.presentation.controllers.base.BaseController;
import com.lumiere.presentation.dtos.auth.CreateUserRequestDTO;
import com.lumiere.presentation.dtos.auth.CreateUserResponseDTO;
import com.lumiere.presentation.dtos.auth.LoginUserRequestDTO;
import com.lumiere.presentation.dtos.auth.LoginUserResponseDTO;
import com.lumiere.presentation.mappers.auth.CreateUserMapper;
import com.lumiere.presentation.mappers.auth.LoginUserMapper;
import com.lumiere.shared.annotations.Loggable;
import com.lumiere.application.dtos.auth.CreateUserDTO;
import com.lumiere.application.dtos.auth.CreateUserResponse;
import com.lumiere.application.dtos.auth.LoginDTO;
import com.lumiere.application.dtos.auth.LoginResponse;
import com.lumiere.application.interfaces.ICreateUserUseCase;
import com.lumiere.application.interfaces.ILoginUseCase;
import com.lumiere.infrastructure.http.cookies.CookieFactory;
import com.lumiere.presentation.routes.Routes;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class AuthController extends BaseController {

    private final ICreateUserUseCase createUserUseCase;
    private final ILoginUseCase loginUseCase;
    private final CreateUserMapper createUserMapper;
    private final LoginUserMapper loginUserMapper;

    public AuthController(
            ICreateUserUseCase createUserUseCase,
            ILoginUseCase loginUseCase,
            CreateUserMapper createUserMapper,
            LoginUserMapper loginUserMapper) {
        this.createUserUseCase = createUserUseCase;
        this.loginUseCase = loginUseCase;
        this.createUserMapper = createUserMapper;
        this.loginUserMapper = loginUserMapper;
    }

    @Loggable
    @PostMapping(Routes.AUTH.REGISTER)
    public ResponseEntity<CreateUserResponseDTO> registerUser(
            @Valid @RequestBody CreateUserRequestDTO requestDTO,
            HttpServletResponse response) {

        CreateUserDTO appDTO = createUserMapper.toApplicationDTO(requestDTO);
        CreateUserResponse responseDTO = createUserUseCase.execute(appDTO);

        response.addCookie(CookieFactory.createAccessTokenCookie(responseDTO.accessToken()));
        response.addCookie(CookieFactory.createRefreshTokenCookie(responseDTO.refreshToken()));

        CreateUserResponseDTO body = createUserMapper.toPresentationDTO(responseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @Loggable
    @PostMapping(Routes.AUTH.LOGIN)
    public ResponseEntity<LoginUserResponseDTO> loginUser(
            @Valid @RequestBody LoginUserRequestDTO requestDTO, HttpServletResponse response) {

        LoginDTO appDTO = loginUserMapper.toApplicationDTO(requestDTO);
        LoginResponse responseDTO = loginUseCase.execute(appDTO);

        response.addCookie(CookieFactory.createAccessTokenCookie(responseDTO.accessToken()));
        response.addCookie(CookieFactory.createRefreshTokenCookie(responseDTO.refreshToken()));

        LoginUserResponseDTO body = loginUserMapper.toPresentationDTO(responseDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(body);
    }
}
