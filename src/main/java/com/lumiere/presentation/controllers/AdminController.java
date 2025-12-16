package com.lumiere.presentation.controllers;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lumiere.application.dtos.admin.command.add.AddProductInput;
import com.lumiere.application.dtos.admin.command.add.AddProductOutput;
import com.lumiere.application.dtos.admin.command.add.AddProductRequestData;
import com.lumiere.application.dtos.admin.command.coupon.GlobalCouponInput;
import com.lumiere.application.dtos.admin.command.coupon.GlobalCouponOutput;
import com.lumiere.application.dtos.admin.command.modify.ModifyProductInput;
import com.lumiere.application.dtos.admin.command.modify.ModifyProductOutput;
import com.lumiere.application.dtos.admin.command.modify.ModifyProductRequestData;
import com.lumiere.application.dtos.admin.command.price.UpdatePriceInput;
import com.lumiere.application.dtos.admin.command.price.UpdatePriceOutput;
import com.lumiere.application.dtos.admin.command.price.UpdatePriceRequestData;
import com.lumiere.application.dtos.admin.command.remove.RemoveProductInput;
import com.lumiere.application.dtos.admin.command.remove.RemoveProductOutput;
import com.lumiere.application.dtos.admin.command.remove.RemoveSingleProductInput;
import com.lumiere.application.dtos.admin.command.stock.decrease.DecreaseStockInput;
import com.lumiere.application.dtos.admin.command.stock.decrease.DecreaseStockOutput;
import com.lumiere.application.dtos.admin.command.stock.decrease.DecreaseStockRequestData;
import com.lumiere.application.dtos.admin.command.stock.increase.IncreaseStockInput;
import com.lumiere.application.dtos.admin.command.stock.increase.IncreaseStockOutput;
import com.lumiere.application.dtos.admin.command.stock.increase.IncreaseStockRequestData;
import com.lumiere.application.interfaces.admin.IAddProductUseCase;
import com.lumiere.application.interfaces.admin.IDecreaseStockUseCase;
import com.lumiere.application.interfaces.admin.IGlobalCouponsUseCase;
import com.lumiere.application.interfaces.admin.IIncreaseStockUseCase;
import com.lumiere.application.interfaces.admin.IModifyProductUseCase;
import com.lumiere.application.interfaces.admin.IRemoveProductUseCase;
import com.lumiere.application.interfaces.admin.IUpdatePriceUseCase;
import com.lumiere.presentation.routes.Routes;
import com.lumiere.shared.annotations.api.ApiVersion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    private final IGlobalCouponsUseCase globalCouponsUseCase;
    private final IRemoveProductUseCase removeProductUseCase;

    public AdminController(IAddProductUseCase addProductUseCase, IModifyProductUseCase modifyProductUseCase,
            IIncreaseStockUseCase increaseStockUseCase, IDecreaseStockUseCase decreaseStockUseCase,
            IUpdatePriceUseCase updatePriceUseCase, IGlobalCouponsUseCase globalCouponsUseCase,
            IRemoveProductUseCase removeProductUseCase) {
        this.addProductUseCase = addProductUseCase;
        this.modifyProductUseCase = modifyProductUseCase;
        this.increaseStockUseCase = increaseStockUseCase;
        this.decreaseStockUseCase = decreaseStockUseCase;
        this.updatePriceUseCase = updatePriceUseCase;
        this.globalCouponsUseCase = globalCouponsUseCase;
        this.removeProductUseCase = removeProductUseCase;
    }

    @ApiVersion("1")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('PRODUCT_ADD')")
    @Operation(summary = "Add multiple products", security = @SecurityRequirement(name = "cookieAuth"))
    @PostMapping(path = Routes.PRIVATE.ADMIN.ADD_MULTIPLE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AddProductOutput> addProductMultiple(@Valid @ModelAttribute AddProductInput req) {
        AddProductOutput output = addProductUseCase.execute(req);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(output);
    }

    @ApiVersion("1")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('PRODUCT_ADD')")
    @Operation(summary = "Add a single product", security = @SecurityRequirement(name = "cookieAuth"))
    @PostMapping(path = Routes.PRIVATE.ADMIN.ADD_SINGLE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AddProductOutput> addProductSingle(@Valid @ModelAttribute AddProductRequestData reqForm,
            HttpServletResponse res) throws IOException {

        AddProductRequestData itemData = new AddProductRequestData(reqForm.name(), reqForm.description(),
                reqForm.priceAmount(), reqForm.currency(), reqForm.stockQuantity(), reqForm.category(),
                reqForm.subcategory(), reqForm.imageFile());

        AddProductInput request = new AddProductInput(List.of(itemData));
        AddProductOutput appDTO = addProductUseCase.execute(request);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(appDTO);
    }

    @ApiVersion("1")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('PRODUCT_UPDATE')")
    @Operation(summary = "Update an existing product completely", security = @SecurityRequirement(name = "cookieAuth"))
    @PutMapping(Routes.PRIVATE.ADMIN.UPDATE_PRODUCT + "/{id}")
    public ResponseEntity<ModifyProductOutput> putProduct(@PathVariable UUID id,
            @Valid @RequestBody ModifyProductRequestData req, HttpServletResponse res) {
        if (!req.isCompleteUpdate())
            return ResponseEntity.badRequest().build();

        ModifyProductInput appDTO = new ModifyProductInput(id, req);
        ModifyProductOutput responseDTO = modifyProductUseCase.execute(appDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

    @ApiVersion("1")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('PRODUCT_UPDATE')")
    @Operation(summary = "Update an existing product partially", security = @SecurityRequirement(name = "cookieAuth"))
    @PatchMapping(Routes.PRIVATE.ADMIN.UPDATE_PRODUCT + "/{id}")
    public ResponseEntity<ModifyProductOutput> patchProduct(@PathVariable UUID id,
            @Valid @RequestBody ModifyProductRequestData req, HttpServletResponse res) {

        if (!req.hasUpdates())
            return ResponseEntity.badRequest().build();

        ModifyProductInput appDTO = new ModifyProductInput(id, req);
        ModifyProductOutput responseDTO = modifyProductUseCase.execute(appDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

    @ApiVersion("1")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('PRODUCT_UPDATE')")
    @Operation(summary = "Increase stock quantity of a product", security = @SecurityRequirement(name = "cookieAuth"))
    @PatchMapping(Routes.PRIVATE.ADMIN.INCREASE_STOCK + "/{id}")
    public ResponseEntity<IncreaseStockOutput> increaseStock(@PathVariable UUID id,
            @Valid @RequestBody IncreaseStockRequestData req, HttpServletResponse res) {

        IncreaseStockInput appDTO = new IncreaseStockInput(id, req);
        IncreaseStockOutput responseDTO = increaseStockUseCase.execute(appDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

    @ApiVersion("1")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('PRODUCT_UPDATE')")
    @Operation(summary = "Decrease stock quantity of a product", security = @SecurityRequirement(name = "cookieAuth"))
    @PatchMapping(Routes.PRIVATE.ADMIN.DECREASE_STOCK + "/{id}")
    public ResponseEntity<DecreaseStockOutput> decreaseStock(@PathVariable UUID id,
            @Valid @RequestBody DecreaseStockRequestData req, HttpServletResponse res) {

        DecreaseStockInput appDTO = new DecreaseStockInput(id, req);
        DecreaseStockOutput responseDTO = decreaseStockUseCase.execute(appDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

    @ApiVersion("1")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('PRODUCT_UPDATE')")
    @Operation(summary = "Update the price of a product", security = @SecurityRequirement(name = "cookieAuth"))
    @PatchMapping(Routes.PRIVATE.ADMIN.UPDATE_PRICE + "/{id}")
    public ResponseEntity<UpdatePriceOutput> updatePrice(@PathVariable UUID id,
            @Valid @RequestBody UpdatePriceRequestData req, HttpServletResponse res) {

        UpdatePriceInput appDTO = new UpdatePriceInput(id, req);
        UpdatePriceOutput responseDTO = updatePriceUseCase.execute(appDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

    @ApiVersion("1")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('COUPON_ADD')")
    @Operation(summary = "Add a global coupon", security = @SecurityRequirement(name = "cookieAuth"))
    @PostMapping(Routes.PRIVATE.ADMIN.GLOBAL_COUPON)
    public ResponseEntity<GlobalCouponOutput> addGlobalCoupon(@Valid @RequestBody GlobalCouponInput req) {
        GlobalCouponOutput response = globalCouponsUseCase.execute(req);
        return ResponseEntity.ok(response);
    }

    @ApiVersion("1")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('PRODUCT_DELETE')")
    @Operation(summary = "Delete multiple products", security = @SecurityRequirement(name = "cookieAuth"))
    @DeleteMapping(Routes.PRIVATE.ADMIN.DELETE_PRODUCTS)
    public ResponseEntity<RemoveProductOutput> deleteProduct(@Valid @RequestBody RemoveProductInput req) {
        RemoveProductOutput response = removeProductUseCase.execute(req);
        return ResponseEntity.ok(response);
    }

    @ApiVersion("1")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('PRODUCT_DELETE')")
    @Operation(summary = "Delete a single product", security = @SecurityRequirement(name = "cookieAuth"))
    @DeleteMapping(Routes.PRIVATE.ADMIN.DELETE_PRODUCT)
    public ResponseEntity<RemoveProductOutput> deleteProduct(@Valid @RequestBody RemoveSingleProductInput req) {
        RemoveProductInput request = new RemoveProductInput(List.of(req.productId()));
        RemoveProductOutput response = removeProductUseCase.execute(request);
        return ResponseEntity.ok(response);
    }
}
