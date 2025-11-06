package com.lumiere.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lumiere.shared.annotations.validators.ValidEntityGraphPathsValidator;
import com.lumiere.shared.annotations.validators.ValidEntityGraphPaths;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import jakarta.persistence.metamodel.Attribute;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ValidEntityGraphPathsValidatorTest {

    @SuppressWarnings("unused")
    private static class User {
        String name;
        Address address;
    }

    @SuppressWarnings("unused")
    private static class Address {
        String street;
        City city;
    }

    @SuppressWarnings("unused")
    private static class City {
        String name;
    }

    @Mock
    private EntityManager em;

    @InjectMocks
    private ValidEntityGraphPathsValidator validator;

    private ValidEntityGraphPaths createFakeAnnotation(Class<?> root, String[] allowed) {
        return new ValidEntityGraphPaths() {
            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return ValidEntityGraphPaths.class;
            }

            @Override
            public Class<?> root() {
                return root;
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
        ValidEntityGraphPaths annotation = createFakeAnnotation(Object.class, new String[] { "user.name", "email" });
        validator.initialize(annotation);

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(
                ConstraintValidatorContext.ConstraintViolationBuilder.class);

        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        doNothing().when(context).disableDefaultConstraintViolation();

        assertThat(validator.isValid(new String[] { "user.name" }, context)).isTrue();
        assertThat(validator.isValid(new String[] { "email" }, context)).isTrue();

        String invalidPath = "invalid";
        assertThat(validator.isValid(new String[] { invalidPath }, context)).isFalse();

        verify(context)
                .buildConstraintViolationWithTemplate("Path not allowed by security whitelist");
        verify(context).disableDefaultConstraintViolation();

        assertThat(validator.isValid(null, context)).isTrue();
        assertThat(validator.isValid(new String[] {}, context)).isTrue();

        assertThat(validator.isValid(new String[] { " " }, context)).isTrue();
        assertThat(validator.isValid(new String[] { "user.name", "", null, " ", "email" }, context)).isTrue();

        verify(em, never()).getMetamodel();
    }

    @Test
    void shouldValidateAllowedPathsAsCollectionCorrectly() {
        ValidEntityGraphPaths annotation = createFakeAnnotation(Object.class, new String[] { "item.id", "date" });
        validator.initialize(annotation);

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        List<String> validPaths = Arrays.asList("item.id", "date", null, " ");

        assertThat(validator.isValid(validPaths.toArray(new String[0]), context)).isTrue();

        List<String> invalidPaths = Arrays.asList("item.id", "wrong.path");

        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(
                ConstraintValidatorContext.ConstraintViolationBuilder.class);

        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        doNothing().when(context).disableDefaultConstraintViolation();

        assertThat(validator.isValid(invalidPaths.toArray(new String[0]), context)).isFalse();

        verify(context).buildConstraintViolationWithTemplate("Path not allowed by security whitelist");
        verify(em, never()).getMetamodel();
    }

    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    void shouldValidateNestedMetamodelPathCorrectly() {
        ValidEntityGraphPaths annotation = createFakeAnnotation(User.class, new String[] {});
        validator.initialize(annotation);

        Metamodel metamodel = mock(Metamodel.class);
        when(em.getMetamodel()).thenReturn(metamodel);

        EntityType<User> userType = mock(EntityType.class);
        EntityType<Address> addressType = mock(EntityType.class);
        EntityType<City> cityType = mock(EntityType.class);

        Attribute<?, ?> addressAttr = mock(Attribute.class);
        Attribute<?, ?> cityAttr = mock(Attribute.class);
        Attribute<?, ?> nameAttr = mock(Attribute.class);

        when(metamodel.entity(User.class)).thenReturn((EntityType) userType);

        when(userType.getAttribute("address")).thenReturn((Attribute) addressAttr);
        when(addressAttr.getJavaType()).thenReturn((Class) Address.class);

        when(metamodel.entity(Address.class)).thenReturn((EntityType) addressType);
        when(addressType.getAttribute("city")).thenReturn((Attribute) cityAttr);
        when(cityAttr.getJavaType()).thenReturn((Class) City.class);

        when(metamodel.entity(City.class)).thenReturn((EntityType) cityType);
        when(cityType.getAttribute("name")).thenReturn((Attribute) nameAttr);
        when(nameAttr.getJavaType()).thenReturn((Class) String.class);

        String subgraphPath = "address.city.name";
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        assertThat(validator.isValid(new String[] { subgraphPath }, context)).isTrue();

        verify(em, times(3)).getMetamodel();
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldRejectInvalidNestedMetamodelPath() {
        ValidEntityGraphPaths annotation = createFakeAnnotation(User.class, new String[] {});
        validator.initialize(annotation);

        Metamodel metamodel = mock(Metamodel.class);
        EntityType<User> userType = mock(EntityType.class);
        when(em.getMetamodel()).thenReturn(metamodel);
        when(metamodel.entity(User.class)).thenReturn(userType);

        when(userType.getAttribute(anyString())).thenThrow(new IllegalArgumentException());

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(
                ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        doNothing().when(context).disableDefaultConstraintViolation();

        String invalidPath = "invalid.field";

        assertThat(validator.isValid(new String[] { invalidPath }, context)).isFalse();

        String expectedMessage = "Invalid Entity Path";
        verify(context).buildConstraintViolationWithTemplate(expectedMessage);
    }
}