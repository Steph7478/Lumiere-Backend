package com.lumiere.application.dtos.auth.response.confirmation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.servlet.http.Cookie;
import java.util.List;

public record LogoutResponse(@JsonIgnore List<Cookie> cookiesToClear, String message) {
    public LogoutResponse(List<Cookie> cookiesToClear) {
        this(cookiesToClear, "Logout successfully");
    }
}