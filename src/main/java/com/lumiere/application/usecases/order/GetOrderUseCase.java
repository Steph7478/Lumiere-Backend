package com.lumiere.application.usecases.order;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.order.query.GetOrderInProgressOutput;
import com.lumiere.application.dtos.order.query.GetOrderInput;
import com.lumiere.application.dtos.order.query.GetOrdersOutput;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.exceptions.order.OrderNotFoundException;
import com.lumiere.domain.entities.Order;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.enums.StatusEnum.Status;
import com.lumiere.domain.readmodels.OrderReadModel;
import com.lumiere.domain.repositories.OrderRepository;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.infrastructure.mappers.OrderMapper;

@Service
public class GetOrderUseCase {
    private final UserRepository userRepo;
    private final OrderRepository orderRepo;
    private final OrderMapper orderMapper;

    protected GetOrderUseCase(UserRepository userRepo, OrderRepository orderRepo, OrderMapper orderMapper) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.orderMapper = orderMapper;
    }

    public GetOrderInProgressOutput getOrderInProgress(GetOrderInput input) {
        User user = userRepo.findUserByAuthId(input.userId()).orElseThrow(UserNotFoundException::new);
        Order order = orderRepo.findByUserIdAndStatus(user.getId(), Status.IN_PROGRESS)
                .orElseThrow(OrderNotFoundException::new);
        OrderReadModel toResponse = orderMapper.toReadModel(order);
        return new GetOrderInProgressOutput(toResponse);
    }

    public GetOrdersOutput GetMultipleOrders(GetOrderInput input) {
        User user = userRepo.findUserByAuthId(input.userId()).orElseThrow(UserNotFoundException::new);
        List<Order> order = orderRepo.findOrderByUserId(user.getId());
        List<OrderReadModel> toResponse = order.stream().map(orderMapper::toReadModel).toList();
        return new GetOrdersOutput(toResponse);
    }
}
