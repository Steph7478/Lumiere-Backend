package com.lumiere.shared.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Roles {
    ADMIN(EnumSet.of(
            Permissions.ADMIN_PRODUCT_ADD,
            Permissions.ADMIN_PRODUCT_DELETE,
            Permissions.ADMIN_PRODUCT_UPDATE,
            Permissions.ADMIN_COUPON_ADD)),

    USER(EnumSet.of(Permissions.USER_READ));

    @Getter
    private final Set<Permissions> permissions;

    @Getter
    private final String role = this.name();

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

    public static Optional<Roles> safeOf(String name) {
        try {
            return Optional.of(valueOf(name));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
