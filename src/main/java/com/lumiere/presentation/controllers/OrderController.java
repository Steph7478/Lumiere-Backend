package com.lumiere.presentation.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.lumiere.application.dtos.order.query.GetOrderInProgressOutput;
import com.lumiere.application.dtos.order.query.GetOrderInput;
import com.lumiere.application.dtos.order.query.GetOrdersOutput;
import com.lumiere.application.interfaces.order.IAddCouponOrderUseCase;
import com.lumiere.application.interfaces.order.IAddItemOrderUsecase;
import com.lumiere.application.interfaces.order.ICancelOrderUseCase;
import com.lumiere.application.interfaces.order.ICreateOrderUseCase;
import com.lumiere.application.interfaces.order.IRemoveItemOrderUseCase;
import com.lumiere.application.usecases.order.GetOrderUseCase;
import com.lumiere.presentation.routes.Routes;
import com.lumiere.shared.annotations.api.ApiVersion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
public class OrderController {

    private final ICreateOrderUseCase createOrderUseCase;
    private final IAddItemOrderUsecase addItemOrderUsecase;
    private final IRemoveItemOrderUseCase removeItemOrderUseCase;
    private final IAddCouponOrderUseCase addCouponOrderUseCase;
    private final ICancelOrderUseCase cancelOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;

    protected OrderController(
            ICreateOrderUseCase createOrderUseCase,
            IAddItemOrderUsecase addItemOrderUsecase,
            IRemoveItemOrderUseCase removeItemOrderUseCase,
            IAddCouponOrderUseCase addCouponOrderUseCase,
            ICancelOrderUseCase cancelOrderUseCase,
            GetOrderUseCase getOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.addItemOrderUsecase = addItemOrderUsecase;
        this.addCouponOrderUseCase = addCouponOrderUseCase;
        this.removeItemOrderUseCase = removeItemOrderUseCase;
        this.cancelOrderUseCase = cancelOrderUseCase;
        this.getOrderUseCase = getOrderUseCase;
    }

    @ApiVersion("1")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Create a new order for authenticated user", security = @SecurityRequirement(name = "cookieAuth"))
    @PostMapping(Routes.PRIVATE.ORDER.CREATE)
    public ResponseEntity<CreateOrderOutput> createOrder(
            @AuthenticationPrincipal UUID userId,
            @RequestBody @Valid CreateOrderRequestData requestWrapper) {

        CreateOrderInput request = new CreateOrderInput(userId, requestWrapper);
        CreateOrderOutput response = createOrderUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

    @ApiVersion("1")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Add an item to the current order", security = @SecurityRequirement(name = "cookieAuth"))
    @PostMapping(Routes.PRIVATE.ORDER.ADD)
    public ResponseEntity<AddItemOrderOutput> addItemToOrder(
            @AuthenticationPrincipal UUID userId,
            @RequestBody @Valid AddItemOrderRequestData requestData) {

        AddItemOrderInput request = new AddItemOrderInput(userId, requestData);
        AddItemOrderOutput response = addItemOrderUsecase.execute(request);
        return ResponseEntity.ok(response);
    }

    @ApiVersion("1")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Remove an item from the current order", security = @SecurityRequirement(name = "cookieAuth"))
    @PostMapping(Routes.PRIVATE.ORDER.REMOVE)
    public ResponseEntity<RemoveItemOutput> removeItemOrder(
            @AuthenticationPrincipal UUID userId,
            @RequestBody @Valid RemoveItemRequestData reqData) {

        RemoveItemOrderInput request = new RemoveItemOrderInput(userId, reqData);
        RemoveItemOutput response = removeItemOrderUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

    @ApiVersion("1")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Apply a coupon to the current order", security = @SecurityRequirement(name = "cookieAuth"))
    @PostMapping(Routes.PRIVATE.ORDER.COUPON)
    public ResponseEntity<AddCouponOutput> addCouponToOrder(
            @AuthenticationPrincipal UUID userId,
            @RequestBody @Valid AddCouponRequestData reqData) {

        AddCouponInput request = new AddCouponInput(userId, reqData);
        AddCouponOutput response = addCouponOrderUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

    @ApiVersion("1")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Cancel the current order", security = @SecurityRequirement(name = "cookieAuth"))
    @PostMapping(Routes.PRIVATE.ORDER.CANCEL)
    public ResponseEntity<CancelOrderOutput> cancelOrder(
            @AuthenticationPrincipal UUID userId) {

        CancelOrderInput request = new CancelOrderInput(userId);
        CancelOrderOutput response = cancelOrderUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

    @ApiVersion("1")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get the current order in progress", security = @SecurityRequirement(name = "cookieAuth"))
    @GetMapping(Routes.PRIVATE.ORDER.IN_PROGRESS)
    public ResponseEntity<GetOrderInProgressOutput> getInProgress(
            @AuthenticationPrincipal UUID userId) {

        GetOrderInput request = new GetOrderInput(userId);
        GetOrderInProgressOutput response = getOrderUseCase.getOrderInProgress(request);
        return ResponseEntity.ok(response);
    }

    @ApiVersion("1")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get all orders for authenticated user", security = @SecurityRequirement(name = "cookieAuth"))
    @GetMapping(Routes.PRIVATE.ORDER.BASE)
    public ResponseEntity<GetOrdersOutput> getAllOrders(
            @AuthenticationPrincipal UUID userId) {

        GetOrderInput request = new GetOrderInput(userId);
        GetOrdersOutput response = getOrderUseCase.GetMultipleOrders(request);
        return ResponseEntity.ok(response);
    }
}
