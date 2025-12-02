package com.lumiere.presentation.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lumiere.application.dtos.cart.command.add.AddCartInput;
import com.lumiere.application.dtos.cart.command.add.AddCartOuput;
import com.lumiere.application.dtos.cart.command.add.AddItemsRequestData;
import com.lumiere.application.dtos.cart.command.add.AddItemsRequestData.InnerAddItemsRequestData;
import com.lumiere.application.dtos.cart.command.delete.DeleteCartInput;
import com.lumiere.application.dtos.cart.command.delete.DeleteCartOutput;
import com.lumiere.application.dtos.cart.command.remove.RemoveCartInput;
import com.lumiere.application.dtos.cart.command.remove.RemoveCartOutput;
import com.lumiere.application.dtos.cart.command.remove.RemoveMultipleItemsRequestData;
import com.lumiere.application.dtos.cart.command.remove.RemoveSingleItemRequestData;
import com.lumiere.application.dtos.cart.query.GetCartByIdInput;
import com.lumiere.application.dtos.cart.query.GetCartByIdOutput;
import com.lumiere.application.interfaces.cart.IAddCartUseCase;
import com.lumiere.application.interfaces.cart.IDeleteCartUseCase;
import com.lumiere.application.interfaces.cart.IGetCartByIdUseCase;
import com.lumiere.application.interfaces.cart.IRemoveCartUseCase;
import com.lumiere.domain.vo.CartItem;
import com.lumiere.presentation.routes.Routes;
import com.lumiere.shared.annotations.api.ApiVersion;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
public class CartController {

    private final IAddCartUseCase addCartUseCase;
    private final IRemoveCartUseCase removeCartUseCase;
    private final IGetCartByIdUseCase getCartByIdUseCase;
    private final IDeleteCartUseCase deleteCartUseCase;

    protected CartController(IAddCartUseCase addCartUseCase, IRemoveCartUseCase removeCartUseCase,
            IGetCartByIdUseCase getCartByIdUseCase, IDeleteCartUseCase deleteCartUseCase) {
        this.addCartUseCase = addCartUseCase;
        this.removeCartUseCase = removeCartUseCase;
        this.getCartByIdUseCase = getCartByIdUseCase;
        this.deleteCartUseCase = deleteCartUseCase;
    }

    @ApiVersion("1")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping(Routes.PRIVATE.CART.DELETE_CART)
    public ResponseEntity<DeleteCartOutput> deleteCart(@AuthenticationPrincipal UUID id) {
        DeleteCartInput request = new DeleteCartInput(id);
        DeleteCartOutput response = deleteCartUseCase.execute(request);

        return ResponseEntity.ok(response);
    }

    @ApiVersion("1")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(Routes.PRIVATE.CART.GET_CART)
    public ResponseEntity<GetCartByIdOutput> getCartForUser(@AuthenticationPrincipal UUID userId) {
        GetCartByIdInput request = new GetCartByIdInput(userId, Optional.empty());
        GetCartByIdOutput response = getCartByIdUseCase.execute(request);

        return ResponseEntity.ok(response);
    }

    @ApiVersion("1")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(Routes.PRIVATE.CART.GET_CART + "/{cartId}")
    public ResponseEntity<GetCartByIdOutput> getCartById(@AuthenticationPrincipal UUID userId,
            @PathVariable UUID cartId) {
        GetCartByIdInput request = new GetCartByIdInput(userId, Optional.of(cartId));
        GetCartByIdOutput response = getCartByIdUseCase.execute(request);

        return ResponseEntity.ok(response);
    }

    @ApiVersion("1")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(Routes.PRIVATE.CART.ADD_MULTIPLE)
    public ResponseEntity<AddCartOuput> addToCartMultiple(@AuthenticationPrincipal UUID id,
            @Valid @RequestBody AddItemsRequestData requestData, HttpServletResponse res) {
        AddCartInput request = new AddCartInput(id, requestData);
        AddCartOuput response = addCartUseCase.execute(request);

        return ResponseEntity.ok(response);
    }

    @ApiVersion("1")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(Routes.PRIVATE.CART.ADD_SINGLE)
    public ResponseEntity<AddCartOuput> addToCartSingle(@AuthenticationPrincipal UUID id,
            @Valid @RequestBody InnerAddItemsRequestData requestData, HttpServletResponse res) {

        AddItemsRequestData data = new AddItemsRequestData(List.of(requestData));

        AddCartInput request = new AddCartInput(id, data);

        AddCartOuput response = addCartUseCase.execute(request);

        return ResponseEntity.ok(response);
    }

    @ApiVersion("1")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(Routes.PRIVATE.CART.REMOVE_MULTIPLE)
    public ResponseEntity<RemoveCartOutput> removeToCartMultiple(@AuthenticationPrincipal UUID id,
            @Valid @RequestBody RemoveMultipleItemsRequestData requestData, HttpServletResponse res) {
        RemoveCartInput request = new RemoveCartInput(id, requestData);
        RemoveCartOutput response = removeCartUseCase.execute(request);

        return ResponseEntity.ok(response);
    }

    @ApiVersion("1")
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
