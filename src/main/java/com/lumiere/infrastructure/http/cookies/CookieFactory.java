package com.lumiere.infrastructure.http.cookies;

import jakarta.servlet.http.Cookie;

public class CookieFactory {

    private CookieFactory() {
    }

    public static Cookie createAccessTokenCookie(String token) {
        Cookie cookie = new Cookie("access_token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(15 * 60);

        return cookie;
    }

    public static Cookie createRefreshTokenCookie(String token) {
        Cookie cookie = new Cookie("refresh_token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        return cookie;
    }
}
