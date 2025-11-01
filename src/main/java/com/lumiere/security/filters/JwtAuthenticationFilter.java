package com.lumiere.security.filters;

import com.lumiere.infrastructure.auth.RefreshTokenService;
import com.lumiere.infrastructure.auth.TokenValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req,
            @NonNull HttpServletResponse res,
            @NonNull FilterChain chain) throws ServletException, IOException {

        Optional<String> accessOpt = getCookie(req, "access_token");
        Optional<String> refreshOpt = getCookie(req, "refresh_token");

        String token = accessOpt.orElse(null);

        if (token == null || !TokenValidator.isValid(token)) {
            token = refreshOpt
                    .filter(TokenValidator::isValid)
                    .filter(TokenValidator::isRefreshToken)
                    .map(rt -> {
                        try {
                            return RefreshTokenService.refreshToken(rt);
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .orElse(null);

            if (token != null) {
                Cookie cookie = new Cookie("access_token", token);
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setPath("/");
                res.addCookie(cookie);
            }
        }

        if (token != null) {
            List<String> roles = TokenValidator.getRoles(token);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            TokenValidator.getUserId(token),
                            null,
                            roles.stream().map(r -> "ROLE_" + r).map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList())));
        }

        chain.doFilter(req, res);
    }

    private Optional<String> getCookie(HttpServletRequest req, String name) {
        if (req.getCookies() == null)
            return Optional.empty();
        for (Cookie c : req.getCookies())
            if (name.equals(c.getName()))
                return Optional.of(c.getValue());
        return Optional.empty();
    }
}
