package com.lumiere.application.dtos.auth.command.logout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.servlet.http.Cookie;
import java.util.List;

public record LogoutHandler(@JsonIgnore List<Cookie> cookiesToClear) {
}