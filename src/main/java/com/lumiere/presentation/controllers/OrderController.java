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
import com.lumiere.application.dtos.order.command.add.AddItemsOrderRequestWrapper;
import com.lumiere.application.dtos.order.command.create.CreateOrderInput;
import com.lumiere.application.dtos.order.command.create.CreateOrderOutput;
import com.lumiere.application.dtos.order.command.create.CreateOrderRequestData;
import com.lumiere.application.interfaces.order.IAddItemOrderUsecase;
import com.lumiere.application.interfaces.order.ICreateOrderUseCase;
import com.lumiere.presentation.controllers.base.BaseController;
import com.lumiere.presentation.routes.Routes;

import jakarta.validation.Valid;

@RestController
public class OrderController extends BaseController {

    private final ICreateOrderUseCase createOrderUseCase;
    private final IAddItemOrderUsecase AddItemOrderUsecase;

    protected OrderController(ICreateOrderUseCase createOrderUseCase, IAddItemOrderUsecase AddItemOrderUsecase) {
        this.createOrderUseCase = createOrderUseCase;
        this.AddItemOrderUsecase = AddItemOrderUsecase;
    }

    @PostMapping(Routes.PRIVATE.ORDER.ORDER_CREATE)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<CreateOrderOutput> createOrder(
            @AuthenticationPrincipal UUID userId,
            @RequestBody @Valid CreateOrderRequestData requestWrapper) {

        CreateOrderInput request = new CreateOrderInput(userId, requestWrapper);
        CreateOrderOutput response = createOrderUseCase.execute(request);

        return ResponseEntity.ok(response);

    }

    @PostMapping(Routes.PRIVATE.ORDER.ORDER_ADD)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<AddItemOrderOutput> addItemToOrder(
            @AuthenticationPrincipal UUID userId,
            @RequestBody @Valid AddItemsOrderRequestWrapper requestData) {

        AddItemOrderInput request = new AddItemOrderInput(userId, requestData);
        AddItemOrderOutput response = AddItemOrderUsecase.execute(request);

        return ResponseEntity.ok(response);

    }
}