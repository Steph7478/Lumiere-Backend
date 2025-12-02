package com.lumiere.application.usecases.order;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.order.command.coupon.AddCouponInput;
import com.lumiere.application.dtos.order.command.coupon.AddCouponOutput;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.exceptions.coupon.CouponNotFoundException;
import com.lumiere.application.exceptions.order.OrderNotFoundException;
import com.lumiere.application.interfaces.order.IAddCouponOrderUseCase;
import com.lumiere.domain.entities.Coupon;
import com.lumiere.domain.entities.Order;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.enums.StatusEnum.Status;
import com.lumiere.domain.repositories.CouponRepository;
import com.lumiere.domain.repositories.OrderRepository;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.domain.services.OrderService;
import com.lumiere.infrastructure.mappers.OrderMapper;

@Service
public class AddCouponOrderUseCase implements IAddCouponOrderUseCase {
    private final UserRepository userRepo;
    private final OrderRepository orderRepo;
    private final CouponRepository couponRepo;
    private final OrderMapper orderReadModel;

    protected AddCouponOrderUseCase(UserRepository userRepo, OrderRepository orderRepo, CouponRepository couponRepo,
            OrderMapper orderReadModel) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.couponRepo = couponRepo;
        this.orderReadModel = orderReadModel;
    }

    @Override
    public AddCouponOutput execute(AddCouponInput input) {
        User user = userRepo.findUserByAuthId(input.userId()).orElseThrow(UserNotFoundException::new);

        Order order = orderRepo.findByUserIdAndStatus(user.getId(), Status.IN_PROGRESS)
                .orElseThrow(OrderNotFoundException::new);

        Coupon coupon = couponRepo.findById(input.coupon().couponId()).orElseThrow(CouponNotFoundException::new);

        Order newOrder = OrderService.useCoupon(order, coupon);

        orderRepo.update(newOrder);

        return new AddCouponOutput(orderReadModel.toReadModel(newOrder));
    }
}
