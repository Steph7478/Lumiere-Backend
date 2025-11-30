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
import com.lumiere.application.dtos.admin.command.modify.ModifyProductRequestData;
import com.lumiere.application.dtos.admin.command.price.UpdatePriceInput;
import com.lumiere.application.dtos.admin.command.price.UpdatePriceOutput;
import com.lumiere.application.dtos.admin.command.price.UpdatePriceRequestData;
import com.lumiere.application.dtos.admin.command.stock.decrease.DecreaseStockInput;
import com.lumiere.application.dtos.admin.command.stock.decrease.DecreaseStockOutput;
import com.lumiere.application.dtos.admin.command.stock.decrease.DecreaseStockRequestData;
import com.lumiere.application.dtos.admin.command.stock.increase.IncreaseStockInput;
import com.lumiere.application.dtos.admin.command.stock.increase.IncreaseStockOutput;
import com.lumiere.application.dtos.admin.command.stock.increase.IncreaseStockRequestData;
import com.lumiere.application.interfaces.admin.IAddProductUseCase;
import com.lumiere.application.interfaces.admin.IDecreaseStockUseCase;
import com.lumiere.application.interfaces.admin.IIncreaseStockUseCase;
import com.lumiere.application.interfaces.admin.IModifyProductUseCase;
import com.lumiere.application.interfaces.admin.IUpdatePriceUseCase;
import com.lumiere.presentation.routes.Routes;
import com.lumiere.shared.annotations.api.ApiVersion;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Validated
@RestController
public class AdminController {
    private final IAddProductUseCase addProductUseCase;
    private final IModifyProductUseCase modifyProductUseCase;
    private final IIncreaseStockUseCase increaseStockUseCase;
    private final IDecreaseStockUseCase decreaseStockUseCase;
    private final IUpdatePriceUseCase updatePriceUseCase;

    public AdminController(IAddProductUseCase addProductUseCase, IModifyProductUseCase modifyProductUseCase,
            IIncreaseStockUseCase increaseStockUseCase, IDecreaseStockUseCase decreaseStockUseCase,
            IUpdatePriceUseCase updatePriceUseCase) {
        this.addProductUseCase = addProductUseCase;
        this.modifyProductUseCase = modifyProductUseCase;
        this.increaseStockUseCase = increaseStockUseCase;
        this.decreaseStockUseCase = decreaseStockUseCase;
        this.updatePriceUseCase = updatePriceUseCase;
    }

    @ApiVersion("v1")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('PRODUCT_ADD')")
    @PostMapping(Routes.PRIVATE.ADMIN.ADD_PRODUCT)
    public ResponseEntity<AddProductOutput> addProduct(@Valid @RequestBody AddProductInput req,
            HttpServletResponse res) {

        AddProductOutput appDTO = addProductUseCase.execute(req);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(appDTO);
    }

    @ApiVersion("v1")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('PRODUCT_UPDATE')")
    @PutMapping(Routes.PRIVATE.ADMIN.UPDATE_PRODUCT + "/{id}")
    public ResponseEntity<ModifyProductOutput> putProduct(@PathVariable UUID id,
            @Valid @RequestBody ModifyProductRequestData req,
            HttpServletResponse res) {
        if (!req.isCompleteUpdate())
            return ResponseEntity.badRequest().build();

        ModifyProductInput appDTO = new ModifyProductInput(id, req);

        ModifyProductOutput responseDTO = modifyProductUseCase.execute(appDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

    @ApiVersion("v1")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('PRODUCT_UPDATE')")
    @PatchMapping(Routes.PRIVATE.ADMIN.UPDATE_PRODUCT + "/{id}")
    public ResponseEntity<ModifyProductOutput> patchProduct(@PathVariable UUID id,
            @Valid @RequestBody ModifyProductRequestData req,
            HttpServletResponse res) {

        if (!req.hasUpdates())
            return ResponseEntity.badRequest().build();

        ModifyProductInput appDTO = new ModifyProductInput(id, req);

        ModifyProductOutput responseDTO = modifyProductUseCase.execute(appDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

    @ApiVersion("v1")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('PRODUCT_UPDATE')")
    @PatchMapping(Routes.PRIVATE.ADMIN.INCREASE_STOCK + "/{id}")
    public ResponseEntity<IncreaseStockOutput> increaseStock(@PathVariable UUID id,
            @Valid @RequestBody IncreaseStockRequestData req, HttpServletResponse res) {

        IncreaseStockInput appDTO = new IncreaseStockInput(id, req);
        IncreaseStockOutput responseDTO = increaseStockUseCase.execute(appDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);

    }

    @ApiVersion("v1")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('PRODUCT_UPDATE')")
    @PatchMapping(Routes.PRIVATE.ADMIN.DECREASE_STOCK + "/{id}")
    public ResponseEntity<DecreaseStockOutput> decreaseStock(@PathVariable UUID id,
            @Valid @RequestBody DecreaseStockRequestData req, HttpServletResponse res) {

        DecreaseStockInput appDTO = new DecreaseStockInput(id, req);
        DecreaseStockOutput responseDTO = decreaseStockUseCase.execute(appDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

    @ApiVersion("v1")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('PRODUCT_UPDATE')")
    @PatchMapping(Routes.PRIVATE.ADMIN.UPDATE_PRICE + "/{id}")
    public ResponseEntity<UpdatePriceOutput> updatePrice(@PathVariable UUID id,
            @Valid @RequestBody UpdatePriceRequestData req, HttpServletResponse res) {

        UpdatePriceInput appDTO = new UpdatePriceInput(id, req);
        UpdatePriceOutput responseDTO = updatePriceUseCase.execute(appDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

}
