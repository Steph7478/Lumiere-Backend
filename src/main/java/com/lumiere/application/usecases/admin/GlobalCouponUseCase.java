package com.lumiere.application.usecases.admin;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.admin.command.coupon.GlobalCouponInput;
import com.lumiere.application.dtos.admin.command.coupon.GlobalCouponOutput;
import com.lumiere.application.interfaces.admin.IGlobalCouponsUseCase;
import com.lumiere.domain.entities.Coupon;
import com.lumiere.domain.repositories.CouponRepository;
import com.lumiere.domain.services.CouponSerivce;
import com.lumiere.shared.annotations.validators.RequireAdmin;

@Service
public class GlobalCouponUseCase implements IGlobalCouponsUseCase {
    private final CouponRepository couponRepo;

    protected GlobalCouponUseCase(CouponRepository couponRepo) {
        this.couponRepo = couponRepo;
    }

    @Override
    @RequireAdmin
    public GlobalCouponOutput execute(GlobalCouponInput input) {
        Coupon coupon = CouponSerivce.globalCoupon(input.category(), input.type(), input.discount(), input.expiredAt(),
                input.code());

        couponRepo.save(coupon);
        return new GlobalCouponOutput(coupon);
    }

}
