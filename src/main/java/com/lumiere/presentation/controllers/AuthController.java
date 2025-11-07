package com.lumiere.presentation.controllers;

import com.lumiere.presentation.controllers.base.BaseController;
import com.lumiere.presentation.dtos.auth.createUser.CreateUserRequestDTO;
import com.lumiere.presentation.dtos.auth.createUser.CreateUserResponseDTO;
import com.lumiere.presentation.dtos.auth.login.LoginUserRequestDTO;
import com.lumiere.presentation.dtos.auth.login.LoginUserResponseDTO;
import com.lumiere.presentation.mappers.auth.CreateUserMapper;
import com.lumiere.presentation.mappers.auth.LoginUserMapper;
import com.lumiere.application.dtos.auth.createUser.CreateUserDTO;
import com.lumiere.application.dtos.auth.createUser.CreateUserResponse;
import com.lumiere.application.dtos.auth.getMe.GetMeRequest;
import com.lumiere.application.dtos.auth.getMe.GetMeResponse;
import com.lumiere.application.dtos.auth.login.LoginDTO;
import com.lumiere.application.dtos.auth.login.LoginResponse;
import com.lumiere.application.interfaces.ICreateUserUseCase;
import com.lumiere.application.interfaces.IGetMeUseCase;
import com.lumiere.application.interfaces.ILoginUseCase;
import com.lumiere.infrastructure.http.cookies.CookieFactory;
import com.lumiere.presentation.routes.Routes;
import com.lumiere.shared.annotations.logs.Loggable;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController extends BaseController {

    private final ICreateUserUseCase createUserUseCase;
    private final ILoginUseCase loginUseCase;
    private final IGetMeUseCase getMeUseCase;
    private final CreateUserMapper createUserMapper;
    private final LoginUserMapper loginUserMapper;

    public AuthController(
            ICreateUserUseCase createUserUseCase,
            ILoginUseCase loginUseCase,
            IGetMeUseCase getMeUseCase,
            CreateUserMapper createUserMapper,
            LoginUserMapper loginUserMapper) {
        this.createUserUseCase = createUserUseCase;
        this.loginUseCase = loginUseCase;
        this.getMeUseCase = getMeUseCase;
        this.createUserMapper = createUserMapper;
        this.loginUserMapper = loginUserMapper;
    }

    @Loggable
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(Routes.PRIVATE.AUTH.ME)
    public ResponseEntity<GetMeResponse> getMe(@AuthenticationPrincipal UUID userId) {
        GetMeRequest request = new GetMeRequest(userId);
        GetMeResponse response = getMeUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

    @Loggable
    @PostMapping(Routes.PUBLIC.AUTH.REGISTER)
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
    @PostMapping(Routes.PUBLIC.AUTH.LOGIN)
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
