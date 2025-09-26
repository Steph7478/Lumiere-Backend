package com.lumiere.security.aspects;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.policies.AdminPolicy;
import com.lumiere.domain.repositories.AuthRepository;
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
    private final AuthRepository authRepository;
    private final HttpServletRequest request;

    public AdminCheckAspect(AdminPolicy adminPolicy, AuthRepository authRepository, HttpServletRequest request) {
        this.adminPolicy = adminPolicy;
        this.authRepository = authRepository;
        this.request = request;
    }

    @Before("@annotation(requireAdmin)")
    public void checkAdmin(RequireAdmin requireAdmin) {
        ActingUser actingUser = (ActingUser) request.getAttribute("actingUser");
        if (actingUser == null) {
            throw new RuntimeException("User not authenticated");
        }

        Auth authUser = authRepository.findById(actingUser.getId());
        if (authUser == null) {
            throw new RuntimeException("User not found");
        }

        if (!adminPolicy.isAdminFull(actingUser, authUser)) {
            throw new RuntimeException("User is not admin");
        }
    }
}
