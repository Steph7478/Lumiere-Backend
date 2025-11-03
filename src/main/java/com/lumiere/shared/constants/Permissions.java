package com.lumiere.shared.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permissions {
    PRODUCT_ADD("PRODUCT_ADD"),
    PRODUCT_DELETE("PRODUCT_DELETE"),
    PRODUCT_UPDATE("PRODUCT_UPDATE"),

    USER_READ("READ_ONLY");

    @Getter
    private final String permission;
}
