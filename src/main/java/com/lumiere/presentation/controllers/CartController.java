package com.lumiere.presentation.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lumiere.application.dtos.cart.command.add.AddCartInput;
import com.lumiere.application.dtos.cart.command.add.AddCartOuput;
import com.lumiere.application.dtos.cart.command.add.AddCartRequestData;
import com.lumiere.application.interfaces.cart.IAddCartUseCase;
import com.lumiere.presentation.controllers.base.BaseController;
import com.lumiere.presentation.routes.Routes;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
public class CartController extends BaseController {

    private final IAddCartUseCase addCartUseCase;

    protected CartController(IAddCartUseCase addCartUseCase) {
        this.addCartUseCase = addCartUseCase;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(Routes.PRIVATE.CART.ADD_CART)
    public ResponseEntity<AddCartOuput> addToCart(@AuthenticationPrincipal UUID id,
            @Valid @RequestBody AddCartRequestData requestData, HttpServletResponse res) {
        AddCartInput request = new AddCartInput(id, requestData);
        AddCartOuput response = addCartUseCase.execute(request);

        return ResponseEntity.ok(response);
    }
}
