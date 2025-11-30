package com.lumiere.presentation.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lumiere.application.dtos.order.command.add.AddItemOrderInput;
import com.lumiere.application.dtos.order.command.add.AddItemOrderOutput;
import com.lumiere.application.dtos.order.command.add.AddItemOrderRequestData;
import com.lumiere.application.dtos.order.command.cancel.CancelOrderInput;
import com.lumiere.application.dtos.order.command.cancel.CancelOrderOutput;
import com.lumiere.application.dtos.order.command.coupon.AddCouponInput;
import com.lumiere.application.dtos.order.command.coupon.AddCouponOutput;
import com.lumiere.application.dtos.order.command.coupon.AddCouponRequestData;
import com.lumiere.application.dtos.order.command.create.CreateOrderInput;
import com.lumiere.application.dtos.order.command.create.CreateOrderOutput;
import com.lumiere.application.dtos.order.command.create.CreateOrderRequestData;
import com.lumiere.application.dtos.order.command.remove.RemoveItemOrderInput;
import com.lumiere.application.dtos.order.command.remove.RemoveItemOutput;
import com.lumiere.application.dtos.order.command.remove.RemoveItemRequestData;
import com.lumiere.application.interfaces.order.IAddCouponOrderUseCase;
import com.lumiere.application.interfaces.order.IAddItemOrderUsecase;
import com.lumiere.application.interfaces.order.ICancelOrderUseCase;
import com.lumiere.application.interfaces.order.ICreateOrderUseCase;
import com.lumiere.application.interfaces.order.IRemoveItemOrderUseCase;
import com.lumiere.presentation.routes.Routes;
import com.lumiere.shared.annotations.api.ApiVersion;

import jakarta.validation.Valid;

@RestController
public class OrderController {

    private final ICreateOrderUseCase createOrderUseCase;
    private final IAddItemOrderUsecase addItemOrderUsecase;
    private final IRemoveItemOrderUseCase removeItemOrderUseCase;
    private final IAddCouponOrderUseCase addCouponOrderUseCase;
    private final ICancelOrderUseCase cancelOrderUseCase;

    protected OrderController(ICreateOrderUseCase createOrderUseCase, IAddItemOrderUsecase addItemOrderUsecase,
            IRemoveItemOrderUseCase removeItemOrderUseCase, IAddCouponOrderUseCase addCouponOrderUseCase,
            ICancelOrderUseCase cancelOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.addItemOrderUsecase = addItemOrderUsecase;
        this.addCouponOrderUseCase = addCouponOrderUseCase;
        this.removeItemOrderUseCase = removeItemOrderUseCase;
        this.cancelOrderUseCase = cancelOrderUseCase;
    }

    @ApiVersion("v1")
    @PostMapping(Routes.PRIVATE.ORDER.ORDER_CREATE)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<CreateOrderOutput> createOrder(
            @AuthenticationPrincipal UUID userId,
            @RequestBody @Valid CreateOrderRequestData requestWrapper) {

        CreateOrderInput request = new CreateOrderInput(userId, requestWrapper);
        CreateOrderOutput response = createOrderUseCase.execute(request);

        return ResponseEntity.ok(response);

    }

    @ApiVersion("v1")
    @PostMapping(Routes.PRIVATE.ORDER.ORDER_ADD)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<AddItemOrderOutput> addItemToOrder(
            @AuthenticationPrincipal UUID userId,
            @RequestBody @Valid AddItemOrderRequestData requestData) {

        AddItemOrderInput request = new AddItemOrderInput(userId, requestData);
        AddItemOrderOutput response = addItemOrderUsecase.execute(request);

        return ResponseEntity.ok(response);

    }

    @ApiVersion("v1")
    @PostMapping(Routes.PRIVATE.ORDER.ORDER_REMOVE)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<RemoveItemOutput> removeItemOrder(@AuthenticationPrincipal UUID userId,
            @RequestBody @Valid RemoveItemRequestData reqData) {

        RemoveItemOrderInput request = new RemoveItemOrderInput(userId, reqData);
        RemoveItemOutput response = removeItemOrderUseCase.execute(request);

        return ResponseEntity.ok(response);
    }

    @ApiVersion("v1")
    @PostMapping(Routes.PRIVATE.ORDER.ORDER_COUPON)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<AddCouponOutput> removeItemOrder(@AuthenticationPrincipal UUID userId,
            @RequestBody @Valid AddCouponRequestData reqData) {

        AddCouponInput request = new AddCouponInput(userId, reqData);
        AddCouponOutput response = addCouponOrderUseCase.execute(request);

        return ResponseEntity.ok(response);
    }

    @ApiVersion("v1")
    @PostMapping(Routes.PRIVATE.ORDER.ORDER_CANCEL)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<CancelOrderOutput> removeItemOrder(@AuthenticationPrincipal UUID userId) {

        CancelOrderInput request = new CancelOrderInput(userId);
        CancelOrderOutput response = cancelOrderUseCase.execute(request);

        return ResponseEntity.ok(response);
    }
}