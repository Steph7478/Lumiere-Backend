package com.lumiere.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lumiere.application.dtos.admin.command.add.AddProductInput;
import com.lumiere.application.dtos.admin.command.add.AddProductOutput;
import com.lumiere.application.usecases.admin.AddProductUseCase;
import com.lumiere.presentation.controllers.base.BaseController;
import com.lumiere.presentation.routes.Routes;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
public class AdminController extends BaseController {
    private final AddProductUseCase addProductUseCase;

    public AdminController(AddProductUseCase addProductUseCase) {
        this.addProductUseCase = addProductUseCase;
    }

    @PreAuthorize("hasRole('ADMIN') and hasAuthority('PRODUCT_ADD')")
    @PostMapping(Routes.PRIVATE.ADMIN.ADD_PRODUCT)
    public ResponseEntity<AddProductOutput> addProduct(@Valid @RequestBody AddProductInput req,
            HttpServletResponse res) {

        AddProductOutput appDTO = addProductUseCase.execute(req);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(appDTO);
    }
}
