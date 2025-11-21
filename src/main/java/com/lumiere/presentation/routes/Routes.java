package com.lumiere.presentation.routes;

public final class Routes {

    private Routes() {
    }

    public static final class PUBLIC {

        public static final class AUTH {
            public static final String REGISTER = "/auth/register";
            public static final String LOGIN = "/auth/login";
        }

        public static final class PRODUCTS {
            public static final String BASE = "/products";
        }
    }

    public static final class PRIVATE {

        public static final class AUTH {
            public static final String ME = "/auth/me";
            public static final String UPDATE = "/auth/update";
            public static final String LOGOUT = "/auth/logout";
            public static final String DELETE = "/auth/delete";
        }

        public static final class USER {
            public static final String PROFILE = "/user/profile";
        }

        public static final class ADMIN {
            public static final String ADD_PRODUCT = "/admin/add-product";
            public static final String UPDATE_PRODUCT = "/admin/update-product";
            public static final String INCREASE_STOCK = "/admin/increase-stock";
            public static final String DECREASE_STOCK = "/admin/decrease-stock";
            public static final String UPDATE_PRICE = "/admin/update-price";
        }

        public static final class CART {
            public static final String ADD_CART = "/cart/add-item";
        }
    }
}
