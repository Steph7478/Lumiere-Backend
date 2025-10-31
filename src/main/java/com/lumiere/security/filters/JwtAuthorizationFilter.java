package com.lumiere.security.filters;

import com.lumiere.security.config.permissions.PermissionConfig;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest req,
            @NonNull HttpServletResponse res,
            @NonNull FilterChain chain) throws ServletException, IOException {

        try {
            String token = getCookie(req, "access_token");
            if (token == null) {
                chain.doFilter(req, res);
                return;
            }

            SignedJWT jwt = SignedJWT.parse(token);
            List<String> roles = jwt.getJWTClaimsSet().getStringListClaim("roles");

            String path = req.getRequestURI();
            String method = req.getMethod();

            var policy = PermissionConfig.getPolicy(path);
            boolean methodOk = policy.methods().isEmpty() || policy.methods().contains(method);
            boolean roleOk = roles.stream().anyMatch(policy.roles()::contains);

            if (!methodOk || !roleOk) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                return;
            }

            chain.doFilter(req, res);

        } catch (ParseException e) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        }
    }

    private String getCookie(HttpServletRequest req, String name) {
        if (req.getCookies() == null)
            return null;
        for (Cookie c : req.getCookies())
            if (c.getName().equals(name))
                return c.getValue();
        return null;
    }
}
