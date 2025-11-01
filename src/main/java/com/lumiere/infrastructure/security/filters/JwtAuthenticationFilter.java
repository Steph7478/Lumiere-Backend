package com.lumiere.infrastructure.security.filters;

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

import com.lumiere.infrastructure.http.auth.TokenService;
import com.lumiere.infrastructure.http.auth.TokenValidator;
import com.lumiere.infrastructure.http.cookies.CookieFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req,
            @NonNull HttpServletResponse res,
            @NonNull FilterChain chain) throws ServletException, IOException {

        Optional<String> accessOpt = CookieFactory.getCookie(req, "access_token");
        Optional<String> refreshOpt = CookieFactory.getCookie(req, "refresh_token");

        String accessToken = accessOpt.orElse(null);
        String refreshToken = refreshOpt.orElse(null);

        if (accessToken == null || !TokenValidator.isValid(accessToken)) {
            if (refreshToken != null && TokenValidator.isRefreshToken(refreshToken)) {
                try {
                    UUID userId = TokenValidator.getUserId(refreshToken);
                    List<String> roles = TokenValidator.getRoles(refreshToken);
                    List<String> permissions = TokenValidator.getPermissions(refreshToken);

                    String newAccessToken = TokenService.generateAccessToken(userId, roles, permissions);

                    Cookie newAccessCookie = CookieFactory.createAccessTokenCookie(newAccessToken);
                    res.addCookie(newAccessCookie);

                    accessToken = newAccessToken;
                } catch (Exception e) {
                    throw new ServletException("Failed to generate Token", e);
                }
            }
        }

        if (accessToken != null && TokenValidator.isValid(accessToken)) {
            List<String> roles = TokenValidator.getRoles(accessToken);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            TokenValidator.getUserId(accessToken),
                            null,
                            roles.stream()
                                    .map(r -> "ROLE_" + r)
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList())));
        }

        chain.doFilter(req, res);
    }
}
