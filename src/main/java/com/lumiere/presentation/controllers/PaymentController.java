package com.lumiere.presentation.controllers;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
public class PaymentController {

    private final ICreateCheckoutSessionUseCase createCheckoutSessionUseCase;

    public PaymentController(ICreateCheckoutSessionUseCase createCheckoutSessionUseCase) {
        this.createCheckoutSessionUseCase = createCheckoutSessionUseCase;
    }

    @ApiVersion("1")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Create a checkout session for payment", security = @SecurityRequirement(name = "cookieAuth"))
    @ApiResponse(responseCode = "200", description = "Checkout session created successfully")
    @PostMapping(Routes.PRIVATE.PAYMENT.PAY)
    public CompletableFuture<ResponseEntity<CreateCheckoutSessionOutput>> payment(
            @AuthenticationPrincipal UUID userId,
            @RequestBody @Valid CreateCheckoutSessionRequestData reqData) {

        CreateCheckoutSessionInput request = new CreateCheckoutSessionInput(userId, reqData);

        return createCheckoutSessionUseCase.execute(request)
                .map(ResponseEntity::ok)
                .toFuture();
    }
}
