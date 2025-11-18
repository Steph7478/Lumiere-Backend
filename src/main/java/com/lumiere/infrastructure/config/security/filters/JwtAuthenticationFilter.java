package com.lumiere.infrastructure.config.security.filters;

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

import com.lumiere.domain.vo.ActingUser;
import com.lumiere.infrastructure.http.auth.mappers.ActingUserExtractor;
import com.lumiere.infrastructure.http.auth.token.TokenService;
import com.lumiere.infrastructure.http.auth.token.TokenValidator;
import com.lumiere.infrastructure.http.cookies.CookieFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
                    ActingUser actingUser = ActingUserExtractor.fromToken(refreshToken);

                    String newAccessToken = TokenService.generateAccessToken(
                            actingUser.getId(),
                            actingUser.getRoles(),
                            actingUser.getPermissions());

                    Cookie newAccessCookie = CookieFactory.createAccessTokenCookie(newAccessToken);
                    res.addCookie(newAccessCookie);

                    accessToken = newAccessToken;
                } catch (Exception e) {
                    throw new ServletException("Failed to generate Token", e);
                }
            }
        }

        if (accessToken != null && TokenValidator.isValid(accessToken)) {
            ActingUser actingUser = ActingUserExtractor.fromToken(accessToken);

            List<SimpleGrantedAuthority> authorities = actingUser.getRoles().stream()
                    .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                    .collect(Collectors.toCollection(ArrayList::new));

            actingUser.getPermissions().stream()
                    .map(SimpleGrantedAuthority::new)
                    .forEach(authorities::add);

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            actingUser.getId(),
                            null,
                            authorities));
        }

        chain.doFilter(req, res);
    }
}