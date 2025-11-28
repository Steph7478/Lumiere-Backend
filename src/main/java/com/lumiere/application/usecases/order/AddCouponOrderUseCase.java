package com.lumiere.application.usecases.order;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.order.command.coupon.AddCouponInput;
import com.lumiere.application.dtos.order.command.coupon.AddCouponOutput;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.exceptions.order.OrderNotFoundException;
import com.lumiere.application.interfaces.order.IAddCouponOrderUseCase;
import com.lumiere.domain.entities.Order;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.enums.StatusEnum.Status;
import com.lumiere.domain.repositories.OrderRepository;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.domain.services.OrderService;

@Service
public class AddCouponOrderUseCase implements IAddCouponOrderUseCase {
    private final UserRepository userRepo;
    private final OrderRepository orderRepo;

    protected AddCouponOrderUseCase(UserRepository userRepo, OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }

    @Override
    public AddCouponOutput execute(AddCouponInput input) {
        User user = userRepo.findUserByAuthId(input.userId()).orElseThrow(UserNotFoundException::new);

        Order order = orderRepo.findByUserIdAndStatus(user.getId(), Status.IN_PROGRESS)
                .orElseThrow(OrderNotFoundException::new);

        Order newOrder = OrderService.useCoupon(order, input.coupon().coupon());

        orderRepo.update(newOrder);

        return new AddCouponOutput();
    }
}
