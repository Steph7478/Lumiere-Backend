package com.lumiere.presentation.middlewares;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lumiere.domain.vo.ActingUser;
import com.lumiere.infrastructure.auth.TokenValidator;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private static final String ACCESS_TOKEN_COOKIE = "access_token";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            Cookie[] cookies = request.getCookies();

            if (cookies != null) {
                Stream.of(cookies)
                        .filter(c -> ACCESS_TOKEN_COOKIE.equals(c.getName()))
                        .map(Cookie::getValue)
                        .filter(TokenValidator::isValid)
                        .filter(TokenValidator::isAccessToken)
                        .findFirst()
                        .ifPresent(token -> {
                            try {
                                UUID userId = TokenValidator.getUserId(token);

                                String rolesJson = TokenValidator.getClaim(token, "roles");
                                String permissionsJson = TokenValidator.getClaim(token, "permissions");

                                Set<String> roles = rolesJson != null
                                        ? objectMapper.readValue(rolesJson, new TypeReference<>() {
                                        })
                                        : Set.of();

                                Set<String> permissions = permissionsJson != null
                                        ? objectMapper.readValue(permissionsJson, new TypeReference<>() {
                                        })
                                        : Set.of();

                                ActingUser actingUser = new ActingUser(userId, roles, permissions);
                                request.setAttribute("actingUser", actingUser);

                                logger.debug("Authenticated user [{}] with roles {} and permissions {}",
                                        userId, roles, permissions);

                            } catch (Exception e) {
                                logger.warn("Failed to parse token claims", e);
                                sendUnauthorized(response, "Invalid token claims");
                                return;
                            }
                        });
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            logger.error("Authentication process failed", e);
            sendUnauthorized(response, "Authentication failed");
        }
    }

    private void sendUnauthorized(HttpServletResponse response, String message) {
        try {
            if (!response.isCommitted()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"" + message + "\"}");
            }
        } catch (IOException e) {
            logger.error("Failed to send unauthorized response", e);
        }
    }

}
