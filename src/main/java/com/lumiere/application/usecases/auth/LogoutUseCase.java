package com.lumiere.application.usecases.auth;

import com.lumiere.application.dtos.auth.query.logout.LogoutHandler;
import com.lumiere.application.interfaces.auth.ILogoutUseCase;
import com.lumiere.infrastructure.http.cookies.CookieFactory;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class LogoutUseCase implements ILogoutUseCase {

    @Override
    public LogoutHandler execute(HttpServletRequest req) {

        Cookie accessTokenCookie = CookieFactory.cleanCookies(req, "access_token");
        Cookie refreshTokenCookie = CookieFactory.cleanCookies(req, "refresh_token");

        List<Cookie> cookiesToClear = Arrays.asList(accessTokenCookie, refreshTokenCookie);

        return new LogoutHandler(cookiesToClear);
    }
}