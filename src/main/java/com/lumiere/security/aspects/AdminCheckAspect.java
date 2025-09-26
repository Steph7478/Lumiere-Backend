package com.lumiere.security.aspects;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.policies.AdminPolicy;
import com.lumiere.domain.vo.ActingUser;
import com.lumiere.security.annotations.RequireAdmin;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AdminCheckAspect {

    private final AdminPolicy adminPolicy;
    private final HttpServletRequest request;

    public AdminCheckAspect(AdminPolicy adminPolicy, HttpServletRequest request) {
        this.adminPolicy = adminPolicy;
        this.request = request;
    }

    @Before("@annotation(requireAdmin)")
    public void checkAdmin(RequireAdmin requireAdmin) {
        ActingUser actingUser = (ActingUser) request.getAttribute("actingUser");
        Auth authUser = (Auth) request.getAttribute("authUser");

        if (actingUser == null || authUser == null || !adminPolicy.isAdminFull(actingUser, authUser)) {
            throw new RuntimeException("User is not admin or not authenticated");
        }
    }
}
