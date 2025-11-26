package com.lumiere.application.usecases.order;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.order.command.create.CreateOrderInput;
import com.lumiere.application.dtos.order.command.create.CreateOrderOutput;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.exceptions.order.OrderAlreadyInProgress;
import com.lumiere.application.exceptions.product.ProductNotFoundException;
import com.lumiere.application.interfaces.order.ICreateOrderUseCase;
import com.lumiere.domain.entities.Order;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.enums.CurrencyEnum.CurrencyType;
import com.lumiere.domain.enums.StatusEnum.Status;
import com.lumiere.domain.repositories.OrderRepository;
import com.lumiere.domain.repositories.ProductRepository;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.domain.services.OrderService;
import com.lumiere.domain.vo.OrderItem;
import com.lumiere.infrastructure.mappers.OrderMapper;

import jakarta.transaction.Transactional;

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
    @Transactional
    public CreateOrderOutput execute(CreateOrderInput input) {
        User user = userRepo.findUserByAuthId(input.authId()).orElseThrow(UserNotFoundException::new);

        CurrencyType currency = input.requestData().currency();
        boolean hasOrderInProgress = orderRepo.existsByUserIdAndStatus(user.getId(), Status.IN_PROGRESS);

        if (hasOrderInProgress)
            throw new OrderAlreadyInProgress();

        Set<UUID> productIds = input.requestData().items().stream()
                .map(itemRequestData -> itemRequestData.productId())
                .collect(Collectors.toSet());

        List<Product> products = productRepo.findAllByIdIn(productIds);

        Map<UUID, Product> productCache = products.stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        List<OrderItem> orderItems = input.requestData().items().stream()
                .map(itemRequestData -> {
                    Product product = productCache.get(itemRequestData.productId());

                    if (product == null)
                        throw new ProductNotFoundException(itemRequestData.productId());

                    return orderItemMapper.toOrderItem(product, itemRequestData, currency);
                })
                .toList();

        Order order = OrderService.createOrder(user, orderItems, currency);
        orderRepo.save(order);

        return new CreateOrderOutput(orderReadModel.toReadModel(order));
    }
}