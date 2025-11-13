package com.lumiere.presentation.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lumiere.application.dtos.admin.command.add.AddProductInput;
import com.lumiere.application.dtos.admin.command.add.AddProductOutput;
import com.lumiere.application.dtos.admin.command.modify.ModifyProductInput;
import com.lumiere.application.dtos.admin.command.modify.ModifyProductOutput;
import com.lumiere.application.usecases.admin.AddProductUseCase;
import com.lumiere.application.usecases.admin.ModifyProductUseCase;
import com.lumiere.presentation.controllers.base.BaseController;
import com.lumiere.presentation.routes.Routes;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@Validated
public class AdminController extends BaseController {
    private final AddProductUseCase addProductUseCase;
    private final ModifyProductUseCase modifyProductUseCase;

    public AdminController(AddProductUseCase addProductUseCase, ModifyProductUseCase modifyProductUseCase) {
        this.addProductUseCase = addProductUseCase;
        this.modifyProductUseCase = modifyProductUseCase;
    }

    @PreAuthorize("hasRole('ADMIN') and hasAuthority('PRODUCT_ADD')")
    @PostMapping(Routes.PRIVATE.ADMIN.ADD_PRODUCT)
    public ResponseEntity<AddProductOutput> addProduct(@Valid @RequestBody AddProductInput req,
            HttpServletResponse res) {

        AddProductOutput appDTO = addProductUseCase.execute(req);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(appDTO);
    }

    @PreAuthorize("hasRole('ADMIN') and hasAuthority('PRODUCT_UPDATE')")
    @PutMapping(Routes.PRIVATE.ADMIN.UPDATE_PRODUCT + "/{id}")
    public ResponseEntity<ModifyProductOutput> putProduct(@PathVariable UUID id,
            @Valid @RequestBody ModifyProductInput req,
            HttpServletResponse res) {
        if (!req.isCompleteUpdate())
            return ResponseEntity.badRequest().build();

        ModifyProductInput appDTO = new ModifyProductInput(id, req.name(), req.description());

        ModifyProductOutput responseDTO = modifyProductUseCase.execute(appDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

    @PreAuthorize("hasRole('ADMIN') and hasAuthority('PRODUCT_UPDATE')")
    @PatchMapping(Routes.PRIVATE.ADMIN.UPDATE_PRODUCT + "/{id}")
    public ResponseEntity<ModifyProductOutput> patchProduct(@PathVariable UUID id,
            @Valid @RequestBody ModifyProductInput req,
            HttpServletResponse res) {

        if (!req.hasUpdates())
            return ResponseEntity.badRequest().build();

        ModifyProductInput appDTO = new ModifyProductInput(id, req.name(), req.description());

        ModifyProductOutput responseDTO = modifyProductUseCase.execute(appDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }
}
