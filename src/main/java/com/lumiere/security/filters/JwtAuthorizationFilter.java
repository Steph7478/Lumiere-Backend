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
import java.util.Set;

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
            Set<String> allowedRoles = PermissionConfig.getRolesForRoute(path);
            Set<String> allowedMethods = PermissionConfig.getMethodsForRoute(path);

            boolean roleOk = roles.stream().anyMatch(allowedRoles::contains);
            boolean methodOk = allowedMethods.isEmpty() || allowedMethods.contains(method);

            if (roleOk && methodOk) {
                chain.doFilter(req, res);
            } else {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            }

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
