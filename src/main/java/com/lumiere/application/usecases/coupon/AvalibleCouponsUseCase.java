package com.lumiere.application.usecases.coupon;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.coupon.query.AvalibleCouponsInput;
import com.lumiere.application.dtos.coupon.query.AvalibleCouponsOutput;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.exceptions.coupon.NoCouponAvalibleException;
import com.lumiere.application.interfaces.coupon.IAvalibleCouponsUseCase;
import com.lumiere.domain.entities.Coupon;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.repositories.CouponRepository;
import com.lumiere.domain.repositories.UserRepository;

@Service
public class AvalibleCouponsUseCase implements IAvalibleCouponsUseCase {
    private final UserRepository userRepo;
    private final CouponRepository couponRepo;

    protected AvalibleCouponsUseCase(UserRepository userRepo, CouponRepository couponRepo) {
        this.couponRepo = couponRepo;
        this.userRepo = userRepo;
    }

    @Override
    public AvalibleCouponsOutput execute(AvalibleCouponsInput input) {
        User user = userRepo.findUserByAuthId(input.userId()).orElseThrow(UserNotFoundException::new);

        List<Coupon> coupons = couponRepo.findAvailableCoupons(user.getId(), Instant.now());

        if (coupons == null)
            throw new NoCouponAvalibleException();

        return new AvalibleCouponsOutput(coupons);
    }

}
