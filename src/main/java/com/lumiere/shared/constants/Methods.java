package com.lumiere.shared.constants;

public enum Methods {
    GET, POST, PUT, DELETE;

    public static Methods fromString(String method) {
        try {
            return Methods.valueOf(method.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}
