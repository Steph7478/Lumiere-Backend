package com.lumiere.application.usecases.order;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.order.command.cancel.CancelOrderInput;
import com.lumiere.application.dtos.order.command.cancel.CancelOrderOutput;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.exceptions.order.OrderNotFoundException;
import com.lumiere.application.interfaces.order.ICancelOrderUseCase;
import com.lumiere.domain.entities.Order;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.enums.StatusEnum.Status;
import com.lumiere.domain.repositories.OrderRepository;
import com.lumiere.domain.repositories.UserRepository;

@Service
public class CancelOrderUseCase implements ICancelOrderUseCase {
    private final OrderRepository orderRepo;
    private final UserRepository userRepo;

    protected CancelOrderUseCase(OrderRepository orderRepo, UserRepository userRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }

    @Override
    public CancelOrderOutput execute(CancelOrderInput input) {
        User user = userRepo.findUserByAuthId(input.userId()).orElseThrow(UserNotFoundException::new);

        Order order = orderRepo.findByUserIdAndStatus(user.getId(), Status.IN_PROGRESS)
                .orElseThrow(OrderNotFoundException::new);

        orderRepo.deleteById(order.getId());

        return new CancelOrderOutput();
    }
}
