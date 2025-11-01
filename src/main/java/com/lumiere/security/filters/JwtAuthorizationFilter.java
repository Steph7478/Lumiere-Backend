package com.lumiere.security.filters;

import com.lumiere.security.config.permissions.RoutePermissions;
import com.lumiere.security.constants.Methods;
import com.lumiere.security.constants.Roles;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req,
            @NonNull HttpServletResponse res,
            @NonNull FilterChain chain) throws ServletException, IOException {

        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            chain.doFilter(req, res);
            return;
        }

        List<Roles> roles = auth.getAuthorities().stream()
                .map(a -> Roles.safeOf(a.getAuthority().replace("ROLE_", "")))
                .flatMap(Optional::stream)
                .toList();

        String path = req.getRequestURI();
        Methods method = Methods.fromString(req.getMethod());

        if (RoutePermissions.PUBLIC_ROUTES.containsKey(path)) {
            chain.doFilter(req, res);
            return;
        }

        var policy = RoutePermissions.PRIVATE_ROUTES.get(path);
        if (policy != null) {
            boolean methodAllowed = policy.methods().contains(method);
            boolean roleAllowed = roles.stream().anyMatch(policy.roles()::contains);

            if (!methodAllowed || !roleAllowed) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                return;
            }
        }

        chain.doFilter(req, res);
    }
}
