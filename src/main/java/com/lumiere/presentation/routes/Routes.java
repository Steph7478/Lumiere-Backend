package com.lumiere.presentation.routes;

public final class Routes {

    private Routes() {
    }

    public static final class PUBLIC {
        public static final class AUTH {
            public static final String BASE = "/auth";
            public static final String REGISTER = BASE + "/register";
            public static final String LOGIN = BASE + "/login";
        }

        public static final class PRODUCTS {
            public static final String BASE = "/products";
            public static final String FILTER = BASE + "/filter";
        }
    }

    public static final class PRIVATE {

        public static final class WEBHOOKS {
            public static final String BASE = "/webhooks";
            public static final String STRIPE = BASE + "/stripe";
        }

        public static final class AUTH {
            public static final String BASE = "/auth";
            public static final String ME = BASE + "/me";
            public static final String UPDATE = BASE + "/update";
            public static final String LOGOUT = BASE + "/logout";
            public static final String DELETE = BASE + "/delete";
        }

        public static final class USER {
            public static final String BASE = "/user";
            public static final String PROFILE = BASE + "/profile";
        }

        public static final class ADMIN {
            public static final String BASE = "/admin";
            public static final String ADD_MULTIPLE = BASE + "/add-multiple";
            public static final String ADD_SINGLE = BASE + "/add-single";
            public static final String UPDATE_PRODUCT = BASE + "/update-product";
            public static final String INCREASE_STOCK = BASE + "/increase-stock";
            public static final String DECREASE_STOCK = BASE + "/decrease-stock";
            public static final String UPDATE_PRICE = BASE + "/update-price";
            public static final String GLOBAL_COUPON = BASE + "/global-coupon";
        }

        public static final class CART {
            public static final String BASE = "/cart";
            public static final String ADD_MULTIPLE = BASE + "/add-multiple";
            public static final String ADD_SINGLE = BASE + "/add-single";
            public static final String REMOVE_MULTIPLE = BASE + "/remove-multiple";
            public static final String REMOVE_SINGLE = BASE + "/remove-single";
            public static final String GET_CART = BASE;
            public static final String DELETE_CART = BASE + "/delete-cart";
        }

        public static final class ORDER {
            public static final String BASE = "/order";
            public static final String CREATE = BASE + "/create";
            public static final String ADD = BASE + "/add-item";
            public static final String REMOVE = BASE + "/remove-item";
            public static final String COUPON = BASE + "/add-coupon";
            public static final String CANCEL = BASE + "/cancel";
            public static final String GET = BASE;
            public static final String IN_PROGRESS = BASE + "/in-progress";
        }

        public static final class COUPON {
            public static final String BASE = "/coupon";
            public static final String AVALIBLE = BASE + "/avalible";

        }
    }
}
