package com.lumiere.presentation.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lumiere.application.dtos.payment.command.checkout.CreateCheckoutSessionInput;
import com.lumiere.application.dtos.payment.command.checkout.CreateCheckoutSessionOutput;
import com.lumiere.application.dtos.payment.command.checkout.CreateCheckoutSessionRequestData;
import com.lumiere.application.interfaces.payment.ICreateCheckoutSessionUseCase;
import com.lumiere.shared.annotations.api.ApiVersion;
import com.lumiere.presentation.routes.Routes;

import jakarta.validation.Valid;

@RestController
public class PaymentController {
    private ICreateCheckoutSessionUseCase createCheckoutSessionUseCase;

    protected PaymentController(ICreateCheckoutSessionUseCase createCheckoutSessionUseCase) {
        this.createCheckoutSessionUseCase = createCheckoutSessionUseCase;
    }

    @ApiVersion("1")
    @PostMapping(Routes.PRIVATE.PAYMENT.PAY)
    public ResponseEntity<CreateCheckoutSessionOutput> payment(@AuthenticationPrincipal UUID userId,
            @RequestBody @Valid CreateCheckoutSessionRequestData reqData) {
        CreateCheckoutSessionInput request = new CreateCheckoutSessionInput(userId, reqData);

        CreateCheckoutSessionOutput response = createCheckoutSessionUseCase.execute(request);

        return ResponseEntity.ok(response);
    }
}
