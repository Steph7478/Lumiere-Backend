package com.lumiere.presentation.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lumiere.application.dtos.coupon.query.AvalibleCouponsInput;
import com.lumiere.application.dtos.coupon.query.AvalibleCouponsOutput;
import com.lumiere.application.interfaces.coupon.IAvalibleCouponsUseCase;
import com.lumiere.presentation.routes.Routes;
import com.lumiere.shared.annotations.api.ApiVersion;

@RestController
public class CouponController {

    private final IAvalibleCouponsUseCase avalibleCouponsUseCase;

    protected CouponController(IAvalibleCouponsUseCase avalibleCouponsUseCase) {
        this.avalibleCouponsUseCase = avalibleCouponsUseCase;
    }

    @ApiVersion("1")
    @GetMapping(Routes.PRIVATE.COUPON.AVALIBLE)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<AvalibleCouponsOutput> getAvalibleCoupons(
            @AuthenticationPrincipal UUID userId) {

        AvalibleCouponsInput request = new AvalibleCouponsInput(userId);
        AvalibleCouponsOutput response = avalibleCouponsUseCase.execute(request);

        return ResponseEntity.ok(response);

    }
}