package com.lumiere.presentation.controllers;

import com.lumiere.presentation.controllers.base.BaseController;
import com.lumiere.application.dtos.auth.command.action.LoginDTO;
import com.lumiere.application.dtos.auth.command.create.CreateUserDTO;
import com.lumiere.application.dtos.auth.command.update.UpdateUserInput;
import com.lumiere.application.dtos.auth.command.update.UpdateUserRequestDTO;
import com.lumiere.application.dtos.auth.query.GetMeRequest;
import com.lumiere.application.dtos.auth.response.auth.CreateUserResponse;
import com.lumiere.application.dtos.auth.response.auth.LoginResponse;
import com.lumiere.application.dtos.auth.response.confirmation.LogoutResponse;
import com.lumiere.application.dtos.auth.response.confirmation.UpdateUserResponseDTO;
import com.lumiere.application.dtos.auth.response.details.GetMeResponse;
import com.lumiere.application.interfaces.ICreateUserUseCase;
import com.lumiere.application.interfaces.IGetMeUseCase;
import com.lumiere.application.interfaces.ILoginUseCase;
import com.lumiere.application.interfaces.ILogoutUseCase;
import com.lumiere.application.interfaces.IUpdateUser;
import com.lumiere.infrastructure.http.cookies.CookieFactory;
import com.lumiere.presentation.routes.Routes;
import com.lumiere.shared.annotations.logs.Loggable;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
    private final IUpdateUser updateUser;
    private final ILogoutUseCase logoutUseCase;

    public AuthController(
            ICreateUserUseCase createUserUseCase,
            ILoginUseCase loginUseCase,
            IGetMeUseCase getMeUseCase,
            IUpdateUser updateUser,
            ILogoutUseCase logoutUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.loginUseCase = loginUseCase;
        this.getMeUseCase = getMeUseCase;
        this.logoutUseCase = logoutUseCase;
        this.updateUser = updateUser;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(Routes.PRIVATE.AUTH.LOGOUT)
    public ResponseEntity<LogoutResponse> logout(HttpServletRequest req, HttpServletResponse res) {
        LogoutResponse response = logoutUseCase.execute(req);
        for (Cookie cookie : response.cookiesToClear()) {
            res.addCookie(cookie);
        }
        return ResponseEntity.ok(response);
    }

    @Loggable
    @PostMapping(Routes.PUBLIC.AUTH.REGISTER)
    public ResponseEntity<CreateUserResponse> registerUser(
            @Valid @RequestBody CreateUserDTO requestDTO,
            HttpServletResponse response) {

        CreateUserResponse responseDTO = createUserUseCase.execute(requestDTO);

        response.addCookie(CookieFactory.createAccessTokenCookie(responseDTO.accessToken()));
        response.addCookie(CookieFactory.createRefreshTokenCookie(responseDTO.refreshToken()));

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Loggable
    @PostMapping(Routes.PUBLIC.AUTH.LOGIN)
    public ResponseEntity<LoginResponse> loginUser(
            @Valid @RequestBody LoginDTO requestDTO, HttpServletResponse response) {

        LoginResponse responseDTO = loginUseCase.execute(requestDTO);

        response.addCookie(CookieFactory.createAccessTokenCookie(responseDTO.accessToken()));
        response.addCookie(CookieFactory.createRefreshTokenCookie(responseDTO.refreshToken()));

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

    @Loggable
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping(Routes.PRIVATE.AUTH.UPDATE)
    public ResponseEntity<UpdateUserResponseDTO> updatePutUser(@AuthenticationPrincipal UUID userId,
            @Valid @RequestBody UpdateUserRequestDTO requestDTO,
            HttpServletResponse response) {

        if (!requestDTO.isCompleteUpdate())
            return ResponseEntity.badRequest().build();

        UpdateUserInput appDTO = new UpdateUserInput(requestDTO, userId);
        UpdateUserResponseDTO responseDTO = updateUser.execute(appDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);

    }

    @Loggable
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PatchMapping(Routes.PRIVATE.AUTH.UPDATE)
    public ResponseEntity<UpdateUserResponseDTO> updatePatchUser(@AuthenticationPrincipal UUID userId,
            @Valid @RequestBody UpdateUserRequestDTO requestDTO,
            HttpServletResponse response) {

        if (!requestDTO.hasUpdates())
            return ResponseEntity.badRequest().build();

        UpdateUserInput appDTO = new UpdateUserInput(requestDTO, userId);
        UpdateUserResponseDTO responseDTO = updateUser.execute(appDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }
}
