package com.lumiere.application.interfaces.order;

import com.lumiere.application.dtos.order.command.coupon.AddCouponInput;
import com.lumiere.application.dtos.order.command.coupon.AddCouponOutput;
import com.lumiere.application.interfaces.base.BaseUseCase;

public interface IAddCouponOrderUseCase extends BaseUseCase<AddCouponInput, AddCouponOutput> {

}
