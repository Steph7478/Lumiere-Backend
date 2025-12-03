package com.lumiere.application.usecases.order;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.order.query.GetOrderInProgressOutput;
import com.lumiere.application.dtos.order.query.GetOrderInput;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.exceptions.order.OrderNotFoundException;
import com.lumiere.application.interfaces.order.IGetOrderInProgressUseCase;
import com.lumiere.domain.entities.Order;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.enums.StatusEnum.Status;
import com.lumiere.domain.readmodels.OrderReadModel;
import com.lumiere.domain.repositories.OrderRepository;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.infrastructure.mappers.OrderMapper;

@Service
public class GetOrderInProgressUseCase implements IGetOrderInProgressUseCase {
    private final UserRepository userRepo;
    private final OrderRepository orderRepo;
    private final OrderMapper orderMapper;

    protected GetOrderInProgressUseCase(UserRepository userRepo, OrderRepository orderRepo, OrderMapper orderMapper) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.orderMapper = orderMapper;
    }

    @Override
    public GetOrderInProgressOutput execute(GetOrderInput input) {
        User user = userRepo.findUserByAuthId(input.userId()).orElseThrow(UserNotFoundException::new);
        Order order = orderRepo.findByUserIdAndStatus(user.getId(), Status.IN_PROGRESS)
                .orElseThrow(OrderNotFoundException::new);
        OrderReadModel toResponse = orderMapper.toReadModel(order);
        return new GetOrderInProgressOutput(toResponse);
    }

}
