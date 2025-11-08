package com.lumiere.application.usecases.auth;

import com.lumiere.application.dtos.response.auth.LogoutResponse;
import com.lumiere.application.interfaces.ILogoutUseCase;
import com.lumiere.infrastructure.http.cookies.CookieFactory;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class LogoutUseCase implements ILogoutUseCase {

    @Override
    public LogoutResponse execute(HttpServletRequest req) {

        Cookie accessTokenCookie = CookieFactory.cleanCookies(req, "access_token");
        Cookie refreshTokenCookie = CookieFactory.cleanCookies(req, "refresh_token");

        List<Cookie> cookiesToClear = Arrays.asList(accessTokenCookie, refreshTokenCookie);

        return new LogoutResponse(cookiesToClear);
    }
}