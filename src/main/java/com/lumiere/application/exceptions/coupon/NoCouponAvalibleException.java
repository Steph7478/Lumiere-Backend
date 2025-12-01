package com.lumiere.application.exceptions.coupon;

import com.lumiere.application.exceptions.base.ApplicationException;

public class NoCouponAvalibleException extends ApplicationException {

    public NoCouponAvalibleException() {
        super("There is no coupon avalible to this account");

    }

}
