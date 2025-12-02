package com.lumiere.application.exceptions.coupon;

import com.lumiere.application.exceptions.base.ApplicationException;

public class CouponNotFoundException extends ApplicationException {

    public CouponNotFoundException() {
        super("No coupon found with this ID");

    }

}
