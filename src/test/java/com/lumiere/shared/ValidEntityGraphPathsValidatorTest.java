package com.lumiere.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lumiere.shared.annotations.validators.ValidEntityGraphPathsValidator;
import com.lumiere.shared.annotations.validators.ValidEntityGraphPaths;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ValidEntityGraphPathsValidatorTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private ValidEntityGraphPathsValidator validator;

    private ValidEntityGraphPaths createFakeAnnotation(String[] allowed) {
        return new ValidEntityGraphPaths() {
            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return ValidEntityGraphPaths.class;
            }

            @Override
            public Class<?> root() {
                return Object.class;
            }

            @Override
            public String[] allowedPaths() {
                return allowed;
            }

            @Override
            public String message() {
                return "Invalid";
            }

            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            @SuppressWarnings("unchecked")
            public Class<? extends Payload>[] payload() {
                return (Class<? extends Payload>[]) new Class[0];
            }
        };
    }

    @Test
    void shouldValidateAllowedPathsCorrectly() {
        ValidEntityGraphPaths annotation = createFakeAnnotation(new String[] { "name", "email" });
        validator.initialize(annotation);

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(
                ConstraintValidatorContext.ConstraintViolationBuilder.class);

        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        doNothing().when(context).disableDefaultConstraintViolation();

        assertThat(validator.isValid(new String[] { "name" }, context)).isTrue();
        assertThat(validator.isValid(new String[] { "email" }, context)).isTrue();

        String invalidPath = "invalid";
        assertThat(validator.isValid(new String[] { invalidPath }, context)).isFalse();

        verify(context)
                .buildConstraintViolationWithTemplate("Path not allowed by security whitelist: '" + invalidPath + "'.");
        verify(context).disableDefaultConstraintViolation();

        assertThat(validator.isValid(null, context)).isTrue();
        assertThat(validator.isValid(new String[] {}, context)).isTrue();

        assertThat(validator.isValid(new String[] { " " }, context)).isTrue();
        assertThat(validator.isValid(new String[] { "name", "", null, " ", "email" }, context)).isTrue();

        verify(em, never()).getMetamodel();
    }
}