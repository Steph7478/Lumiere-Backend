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
            public static final String ADD_MULTIPLE = "/cart/add-multiple";
            public static final String ADD_SINGLE = "/cart/add-single";
            public static final String REMOVE_MULTIPLE = "/cart/remove-multiple";
            public static final String REMOVE_SINGLE = "/cart/remove-single";
            public static final String GET_CART = "/cart";
            public static final String DELETE_CART = "/cart/delete-cart";
        }

        public static final class ORDER {
            public static final String ORDER_CREATE = "/order/create";
            public static final String ORDER_ADD = "/order/add-item";

        }
    }
}
