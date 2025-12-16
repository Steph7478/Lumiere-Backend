package com.lumiere.presentation.controllers;

import com.lumiere.application.dtos.auth.command.create.CreateUserHandler;
import com.lumiere.application.dtos.auth.command.create.CreateUserInput;
import com.lumiere.application.dtos.auth.command.create.CreateUserOutput;
import com.lumiere.application.dtos.auth.command.delete.DeleteUserInput;
import com.lumiere.application.dtos.auth.command.delete.DeleteUserOutput;
import com.lumiere.application.dtos.auth.command.login.LoginHandler;
import com.lumiere.application.dtos.auth.command.login.LoginInput;
import com.lumiere.application.dtos.auth.command.logout.LogoutHandler;
import com.lumiere.application.dtos.auth.command.logout.LogoutOutput;
import com.lumiere.application.dtos.auth.command.update.UpdateUserInput;
import com.lumiere.application.dtos.auth.command.update.UpdateUserOutput;
import com.lumiere.application.dtos.auth.command.update.UpdateUserRequestData;
import com.lumiere.application.dtos.auth.query.me.GetMeInput;
import com.lumiere.application.dtos.auth.query.me.GetMeOutput;
import com.lumiere.application.interfaces.auth.ICreateUserUseCase;
import com.lumiere.application.interfaces.auth.IDeleteUserUseCase;
import com.lumiere.application.interfaces.auth.IGetMeUseCase;
import com.lumiere.application.interfaces.auth.ILoginUseCase;
import com.lumiere.application.interfaces.auth.ILogoutUseCase;
import com.lumiere.application.interfaces.auth.IUpdateUser;
import com.lumiere.infrastructure.http.cookies.CookieFactory;
import com.lumiere.presentation.routes.Routes;
import com.lumiere.shared.annotations.api.ApiVersion;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.util.UUID;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final ICreateUserUseCase createUserUseCase;
    private final ILoginUseCase loginUseCase;
    private final IGetMeUseCase getMeUseCase;
    private final IUpdateUser updateUser;
    private final ILogoutUseCase logoutUseCase;
    private final IDeleteUserUseCase deleteUserUseCase;

    public AuthController(
            ICreateUserUseCase createUserUseCase,
            ILoginUseCase loginUseCase,
            IGetMeUseCase getMeUseCase,
            IUpdateUser updateUser,
            ILogoutUseCase logoutUseCase,
            IDeleteUserUseCase deleteUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.loginUseCase = loginUseCase;
        this.getMeUseCase = getMeUseCase;
        this.logoutUseCase = logoutUseCase;
        this.updateUser = updateUser;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @ApiVersion("1")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get authenticated user profile", security = @SecurityRequirement(name = "cookieAuth"))
    @GetMapping(Routes.PRIVATE.AUTH.ME)
    public ResponseEntity<GetMeOutput> getMe(@AuthenticationPrincipal UUID userId) {
        GetMeInput request = new GetMeInput(userId);
        GetMeOutput response = getMeUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

    @ApiVersion("1")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Logout authenticated user", security = @SecurityRequirement(name = "cookieAuth"))
    @PostMapping(Routes.PRIVATE.AUTH.LOGOUT)
    public ResponseEntity<LogoutOutput> logout(HttpServletRequest req, HttpServletResponse res) {
        LogoutHandler result = logoutUseCase.execute(req);

        for (Cookie cookie : result.cookiesToClear()) {
            res.addCookie(cookie);
        }

        LogoutOutput body = new LogoutOutput();
        return ResponseEntity.ok(body);
    }

    @ApiVersion("1")
    @Operation(summary = "Register a new user")
    @PostMapping(Routes.PUBLIC.AUTH.REGISTER)
    public ResponseEntity<CreateUserOutput> registerUser(
            @Valid @RequestBody CreateUserInput requestDTO,
            HttpServletResponse response) {

        CreateUserHandler responseDTO = createUserUseCase.execute(requestDTO);

        response.addCookie(CookieFactory.createAccessTokenCookie(responseDTO.accessToken()));
        response.addCookie(CookieFactory.createRefreshTokenCookie(responseDTO.refreshToken()));

        CreateUserOutput body = new CreateUserOutput(responseDTO.name(), responseDTO.role());
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @ApiVersion("1")
    @Operation(summary = "Authenticate user and create session")
    @PostMapping(Routes.PUBLIC.AUTH.LOGIN)
    public ResponseEntity<CreateUserOutput> loginUser(
            @Valid @RequestBody LoginInput requestDTO,
            HttpServletResponse response) {

        LoginHandler responseDTO = loginUseCase.execute(requestDTO);

        response.addCookie(CookieFactory.createAccessTokenCookie(responseDTO.accessToken()));
        response.addCookie(CookieFactory.createRefreshTokenCookie(responseDTO.refreshToken()));

        CreateUserOutput body = new CreateUserOutput(responseDTO.name(), responseDTO.role());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(body);
    }

    @ApiVersion("1")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Update authenticated user completely", security = @SecurityRequirement(name = "cookieAuth"))
    @PutMapping(Routes.PRIVATE.AUTH.UPDATE)
    public ResponseEntity<UpdateUserOutput> updatePutUser(
            @AuthenticationPrincipal UUID userId,
            @Valid @RequestBody UpdateUserRequestData requestDTO,
            HttpServletResponse response) {

        if (!requestDTO.isCompleteUpdate())
            return ResponseEntity.badRequest().build();

        UpdateUserInput appDTO = new UpdateUserInput(requestDTO, userId);
        UpdateUserOutput responseDTO = updateUser.execute(appDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

    @ApiVersion("1")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Update authenticated user partially", security = @SecurityRequirement(name = "cookieAuth"))
    @PatchMapping(Routes.PRIVATE.AUTH.UPDATE)
    public ResponseEntity<UpdateUserOutput> updatePatchUser(
            @AuthenticationPrincipal UUID userId,
            @Valid @RequestBody UpdateUserRequestData requestDTO,
            HttpServletResponse response) {

        if (!requestDTO.hasUpdates())
            return ResponseEntity.badRequest().build();

        UpdateUserInput appDTO = new UpdateUserInput(requestDTO, userId);
        UpdateUserOutput responseDTO = updateUser.execute(appDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

    @ApiVersion("1")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Delete authenticated user account", security = @SecurityRequirement(name = "cookieAuth"))
    @DeleteMapping(Routes.PRIVATE.AUTH.DELETE)
    public ResponseEntity<DeleteUserOutput> deleteUser(@AuthenticationPrincipal UUID userId) {

        DeleteUserInput request = new DeleteUserInput(userId);
        DeleteUserOutput response = deleteUserUseCase.execute(request);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
