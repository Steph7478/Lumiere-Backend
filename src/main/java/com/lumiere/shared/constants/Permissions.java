package com.lumiere.shared.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permissions {
    ADMIN_PRODUCT_ADD("PRODUCT_ADD"),
    ADMIN_PRODUCT_DELETE("PRODUCT_DELETE"),
    ADMIN_PRODUCT_UPDATE("PRODUCT_UPDATE"),

    USER_READ("READ_ONLY");

    @Getter
    private final String permission;
}
