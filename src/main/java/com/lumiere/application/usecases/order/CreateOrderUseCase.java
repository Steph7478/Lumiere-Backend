package com.lumiere.application.usecases.order;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.order.command.create.CreateOrderInput;
import com.lumiere.application.dtos.order.command.create.CreateOrderOutput;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.exceptions.product.ProductNotFoundException;
import com.lumiere.application.interfaces.order.ICreateOrderUseCase;
import com.lumiere.domain.entities.Order;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.repositories.OrderRepository;
import com.lumiere.domain.repositories.ProductRepository;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.domain.services.OrderService;
import com.lumiere.domain.vo.OrderItem;
import com.lumiere.infrastructure.mappers.OrderMapper;
import com.lumiere.application.mappers.order.OrderItemUseCaseMapper;

@Service
public class CreateOrderUseCase implements ICreateOrderUseCase {
    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final OrderItemUseCaseMapper orderItemMapper;
    private final OrderMapper orderReadModel;
    private final OrderRepository orderRepo;

    protected CreateOrderUseCase(
            UserRepository userRepo,
            ProductRepository productRepo,
            OrderItemUseCaseMapper orderItemMapper, OrderRepository orderRepo, OrderMapper orderReadModel) {
        this.userRepo = userRepo;
        this.productRepo = productRepo;
        this.orderItemMapper = orderItemMapper;
        this.orderRepo = orderRepo;
        this.orderReadModel = orderReadModel;
    }

    @Override
    public CreateOrderOutput execute(CreateOrderInput input) {
        User user = userRepo.findUserByAuthId(input.authId()).orElseThrow(UserNotFoundException::new);

        List<OrderItem> orderItems = input.requestData().items().stream()
                .map(itemRequestData -> {
                    Product product = productRepo.findById(itemRequestData.productId())
                            .orElseThrow(() -> new ProductNotFoundException(itemRequestData.productId()));

                    return orderItemMapper.toOrderItem(product, itemRequestData);
                })
                .toList();

        Order order = OrderService.createOrder(user, orderItems);
        orderRepo.save(order);

        return new CreateOrderOutput(orderReadModel.toReadModel(order));
    }
}