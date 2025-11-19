package com.lumiere.shared.annotations.validators;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.repositories.AuthRepository;
import com.lumiere.domain.vo.ActingUser;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class RequireAdminValidator implements ConstraintValidator<RequireAdmin, Object> {

    private final AuthRepository authRepository;

    public RequireAdminValidator(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof ActingUser))
            return false;

        ActingUser actingUser = (ActingUser) authentication.getPrincipal();
        UUID authId = actingUser.getId();

        Auth auth = authRepository.findById(authId).orElse(null);
        if (auth == null)
            return false;

        return auth.isAdmin();
    }
}
