package com.lumiere.infrastructure.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lumiere.infrastructure.security.config.permissions.RoutePermissions;
import com.lumiere.infrastructure.security.constants.Methods;
import com.lumiere.infrastructure.security.constants.Roles;

import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req,
            @NonNull HttpServletResponse res,
            @NonNull FilterChain chain) throws ServletException, IOException {

        if (RoutePermissions.PUBLIC_ROUTES.containsKey(req.getRequestURI()) ||
                SecurityContextHolder.getContext().getAuthentication() == null) {
            chain.doFilter(req, res);
            return;
        }

        var policy = RoutePermissions.PRIVATE_ROUTES.get(req.getRequestURI());

        if (policy != null && (!policy.methods().contains(Methods.fromString(req.getMethod())) ||
                SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                        .map(a -> Roles.safeOf(a.getAuthority().replace("ROLE_", "")))
                        .flatMap(o -> o.stream())
                        .noneMatch(policy.roles()::contains))) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        chain.doFilter(req, res);
    }
}
