package com.lumiere.presentation.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.lumiere.application.dtos.admin.command.add.AddProductInput;
import com.lumiere.application.dtos.admin.command.add.AddProductOutput;
import com.lumiere.application.dtos.admin.command.add.AddProductRequestData;
import com.lumiere.application.dtos.admin.command.coupon.GlobalCouponInput;
import com.lumiere.application.dtos.admin.command.coupon.GlobalCouponOutput;
import com.lumiere.application.dtos.admin.command.modify.ModifyProductOutput;
import com.lumiere.application.dtos.admin.command.modify.ModifyProductRequestData;
import com.lumiere.application.dtos.admin.command.price.UpdatePriceOutput;
import com.lumiere.application.dtos.admin.command.price.UpdatePriceRequestData;
import com.lumiere.application.dtos.admin.command.remove.RemoveProductInput;
import com.lumiere.application.dtos.admin.command.remove.RemoveProductOutput;
import com.lumiere.application.dtos.admin.command.stock.decrease.DecreaseStockOutput;
import com.lumiere.application.dtos.admin.command.stock.decrease.DecreaseStockRequestData;
import com.lumiere.application.dtos.admin.command.stock.increase.IncreaseStockOutput;
import com.lumiere.application.dtos.admin.command.stock.increase.IncreaseStockRequestData;
import com.lumiere.application.interfaces.admin.IAddProductUseCase;
import com.lumiere.application.interfaces.admin.IDecreaseStockUseCase;
import com.lumiere.application.interfaces.admin.IGlobalCouponsUseCase;
import com.lumiere.application.interfaces.admin.IIncreaseStockUseCase;
import com.lumiere.application.interfaces.admin.IModifyProductUseCase;
import com.lumiere.application.interfaces.admin.IRemoveProductUseCase;
import com.lumiere.application.interfaces.admin.IUpdatePriceUseCase;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Mock
    private IAddProductUseCase addProductUseCase;
    @Mock
    private IModifyProductUseCase modifyProductUseCase;
    @Mock
    private IIncreaseStockUseCase increaseStockUseCase;
    @Mock
    private IDecreaseStockUseCase decreaseStockUseCase;
    @Mock
    private IUpdatePriceUseCase updatePriceUseCase;
    @Mock
    private IGlobalCouponsUseCase globalCouponsUseCase;
    @Mock
    private IRemoveProductUseCase removeProductUseCase;
    @InjectMocks
    private AdminController adminController;

    @Test
    void should_return_202_when_add_multiple_products() {
        AddProductRequestData req = Mockito.mock(AddProductRequestData.class);
        AddProductOutput output = Mockito.mock(AddProductOutput.class);

        when(addProductUseCase.execute(any()))
                .thenReturn(output);

        ResponseEntity<AddProductOutput> res = adminController
                .addProductMultiple(new AddProductInput(List.of(req)));

        assertEquals(HttpStatus.ACCEPTED, res.getStatusCode());
        verify(addProductUseCase).execute(any());
    }

    @Test
    void should_return_202_when_modify_product_with_put_method() {
        ModifyProductRequestData req = Mockito.mock(ModifyProductRequestData.class);
        ModifyProductOutput output = Mockito.mock(ModifyProductOutput.class);

        when(req.isCompleteUpdate()).thenReturn(true);
        when(modifyProductUseCase.execute(any()))
                .thenReturn(output);

        ResponseEntity<ModifyProductOutput> res = adminController.putProduct(Mockito.mock(UUID.class), req);

        assertEquals(HttpStatus.ACCEPTED, res.getStatusCode());
        verify(modifyProductUseCase).execute(any());
    }

    @Test
    void should_return_202_when_modify_product_with_patch_method() {
        ModifyProductRequestData req = Mockito.mock(ModifyProductRequestData.class);
        ModifyProductOutput output = Mockito.mock(ModifyProductOutput.class);

        when(req.hasUpdates()).thenReturn(true);
        when(modifyProductUseCase.execute(any()))
                .thenReturn(output);

        ResponseEntity<ModifyProductOutput> res = adminController.patchProduct(Mockito.mock(UUID.class), req);

        assertEquals(HttpStatus.ACCEPTED, res.getStatusCode());
        verify(modifyProductUseCase).execute(any());
    }

    @Test
    void should_return_202_when_increase_stock() {
        IncreaseStockRequestData req = Mockito.mock(IncreaseStockRequestData.class);
        IncreaseStockOutput output = Mockito.mock(IncreaseStockOutput.class);

        when(increaseStockUseCase.execute(any())).thenReturn(output);
        ResponseEntity<IncreaseStockOutput> res = adminController.increaseStock(Mockito.mock(UUID.class), req);

        assertEquals(HttpStatus.ACCEPTED, res.getStatusCode());
        verify(increaseStockUseCase).execute(any());
    }

    @Test
    void should_return_202_when_decrease_stock() {
        DecreaseStockRequestData req = Mockito.mock(DecreaseStockRequestData.class);
        DecreaseStockOutput output = Mockito.mock(DecreaseStockOutput.class);

        when(decreaseStockUseCase.execute(any())).thenReturn(output);
        ResponseEntity<DecreaseStockOutput> res = adminController.decreaseStock(Mockito.mock(UUID.class), req);

        assertEquals(HttpStatus.ACCEPTED, res.getStatusCode());
        verify(decreaseStockUseCase).execute(any());
    }

    @Test
    void should_return_202_when_update_price() {
        UpdatePriceRequestData req = Mockito.mock(UpdatePriceRequestData.class);
        UpdatePriceOutput output = Mockito.mock(UpdatePriceOutput.class);

        when(updatePriceUseCase.execute(any())).thenReturn(output);

        ResponseEntity<UpdatePriceOutput> res = adminController.updatePrice(Mockito.mock(UUID.class), req);

        assertEquals(HttpStatus.ACCEPTED, res.getStatusCode());
        verify(updatePriceUseCase).execute(any());
    }

    @Test
    void should_return_200_when_add_global_coupon() {
        GlobalCouponInput req = Mockito.mock(GlobalCouponInput.class);
        GlobalCouponOutput output = Mockito.mock(GlobalCouponOutput.class);

        when(globalCouponsUseCase.execute(any())).thenReturn(output);

        ResponseEntity<GlobalCouponOutput> res = adminController.addGlobalCoupon(req);

        assertEquals(HttpStatus.OK, res.getStatusCode());
        verify(globalCouponsUseCase).execute(any());
    }

    @Test
    void should_return_200_when_delete_multiple_products() {
        RemoveProductInput req = Mockito.mock(RemoveProductInput.class);
        RemoveProductOutput output = Mockito.mock(RemoveProductOutput.class);

        when(removeProductUseCase.execute(any())).thenReturn(output);

        ResponseEntity<RemoveProductOutput> res = adminController.deleteProduct(req);

        assertEquals(HttpStatus.OK, res.getStatusCode());
        verify(removeProductUseCase).execute(any());
    }

}
