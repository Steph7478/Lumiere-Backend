package com.lumiere.application.dtos.auth.response.confirmation;

import jakarta.servlet.http.Cookie;
import java.util.List;

public record LogoutResponse(List<Cookie> cookiesToClear, String message) {
    public LogoutResponse(List<Cookie> cookiesToClear) {
        this(cookiesToClear, "Logout successfully");
    }
}