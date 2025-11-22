package com.lumiere.presentation.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lumiere.application.dtos.cart.command.add.AddCartInput;
import com.lumiere.application.dtos.cart.command.add.AddCartOuput;
import com.lumiere.application.dtos.cart.command.add.AddMultipleItemsRequestData;
import com.lumiere.application.dtos.cart.command.add.AddSingleItemRequestData;
import com.lumiere.application.dtos.cart.command.remove.RemoveCartInput;
import com.lumiere.application.dtos.cart.command.remove.RemoveCartOutput;
import com.lumiere.application.dtos.cart.command.remove.RemoveMultipleItemsRequestData;
import com.lumiere.application.dtos.cart.command.remove.RemoveSingleItemRequestData;
import com.lumiere.application.interfaces.cart.IAddCartUseCase;
import com.lumiere.application.interfaces.cart.IRemoveCartUseCase;
import com.lumiere.domain.vo.CartItem;
import com.lumiere.presentation.controllers.base.BaseController;
import com.lumiere.presentation.routes.Routes;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
public class CartController extends BaseController {

    private final IAddCartUseCase addCartUseCase;
    private final IRemoveCartUseCase removeCartUseCase;

    protected CartController(IAddCartUseCase addCartUseCase, IRemoveCartUseCase removeCartUseCase) {
        this.addCartUseCase = addCartUseCase;
        this.removeCartUseCase = removeCartUseCase;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(Routes.PRIVATE.CART.ADD_MULTIPLE)
    public ResponseEntity<AddCartOuput> addToCartMultiple(@AuthenticationPrincipal UUID id,
            @Valid @RequestBody AddMultipleItemsRequestData requestData, HttpServletResponse res) {
        AddCartInput request = new AddCartInput(id, requestData);
        AddCartOuput response = addCartUseCase.execute(request);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(Routes.PRIVATE.CART.ADD_SINGLE)
    public ResponseEntity<AddCartOuput> addToCartSingle(@AuthenticationPrincipal UUID id,
            @Valid @RequestBody AddSingleItemRequestData requestData, HttpServletResponse res) {

        CartItem singleCartItem = new CartItem(
                requestData.productId(),
                requestData.quantity());

        AddCartInput request = new AddCartInput(
                id,
                new AddMultipleItemsRequestData(List.of(singleCartItem)));

        AddCartOuput response = addCartUseCase.execute(request);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(Routes.PRIVATE.CART.REMOVE_MULTIPLE)
    public ResponseEntity<RemoveCartOutput> removeToCartMultiple(@AuthenticationPrincipal UUID id,
            @Valid @RequestBody RemoveMultipleItemsRequestData requestData, HttpServletResponse res) {
        RemoveCartInput request = new RemoveCartInput(id, requestData);
        RemoveCartOutput response = removeCartUseCase.execute(request);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(Routes.PRIVATE.CART.REMOVE_SINGLE)
    public ResponseEntity<RemoveCartOutput> removeToCartSingle(@AuthenticationPrincipal UUID id,
            @Valid @RequestBody RemoveSingleItemRequestData requestData, HttpServletResponse res) {

        CartItem singleCartItem = new CartItem(
                requestData.productId(),
                requestData.quantity());

        RemoveCartInput request = new RemoveCartInput(
                id,
                new RemoveMultipleItemsRequestData(List.of(singleCartItem)));

        RemoveCartOutput response = removeCartUseCase.execute(request);

        return ResponseEntity.ok(response);
    }
}
