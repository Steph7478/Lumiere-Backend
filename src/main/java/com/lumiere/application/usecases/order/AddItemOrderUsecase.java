package com.lumiere.application.usecases.order;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.order.command.add.AddItemOrderInput;
import com.lumiere.application.dtos.order.command.add.AddItemOrderOutput;
import com.lumiere.application.dtos.order.command.add.AddItemOrderRequestData;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.exceptions.order.OrderNotFoundException;
import com.lumiere.application.interfaces.order.IAddItemOrderUsecase;
import com.lumiere.application.services.ProductCacheService;
import com.lumiere.domain.entities.Order;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.enums.StatusEnum.Status;
import com.lumiere.domain.repositories.OrderRepository;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.domain.services.OrderService;
import com.lumiere.domain.vo.OrderItem;
import com.lumiere.infrastructure.mappers.OrderMapper;

import jakarta.transaction.Transactional;

@Service
public class AddItemOrderUsecase implements IAddItemOrderUsecase {
        private final UserRepository userRepo;
        private final OrderRepository orderRepo;
        private final ProductCacheService productCacheService;
        private final OrderMapper orderReadModel;

        protected AddItemOrderUsecase(UserRepository userRepo, OrderRepository orderRepo,
                        ProductCacheService productCacheService, OrderMapper orderReadModel) {
                this.orderRepo = orderRepo;
                this.orderReadModel = orderReadModel;
                this.userRepo = userRepo;
                this.productCacheService = productCacheService;
        }

        @Override
        @Transactional
        public AddItemOrderOutput execute(AddItemOrderInput input) {
                User user = userRepo.findUserByAuthId(input.userId()).orElseThrow(UserNotFoundException::new);

                Order order = orderRepo.findByUserIdAndStatus(user.getId(), Status.IN_PROGRESS)
                                .orElseThrow(OrderNotFoundException::new);

                Set<UUID> newProductIdsToLoad = input.reqData().items().stream()
                                .map(AddItemOrderRequestData::productId)
                                .filter(Objects::nonNull)
                                .filter(id -> order.getItems().stream()
                                                .noneMatch(item -> item.getProductId().equals(id)))
                                .collect(Collectors.toSet());

                Map<UUID, Product> productMap = productCacheService.loadProductCache(newProductIdsToLoad);

                List<OrderItem> itemsToProcess = input.reqData().items().stream().map(inputItem -> {
                        UUID productId = inputItem.productId();

                        OrderItem existingItem = order.getItems().stream()
                                        .filter(item -> item.getProductId().equals(productId))
                                        .findFirst()
                                        .orElse(null);

                        if (existingItem != null) {
                                return new OrderItem(
                                                existingItem.getProductId(),
                                                existingItem.getName(),
                                                inputItem.quantity(),
                                                existingItem.getUnitPrice());
                        } else {
                                Product product = productMap.get(productId);
                                return new OrderItem(
                                                productId,
                                                product.getName(),
                                                inputItem.quantity(),
                                                product.getPrice().getAmount());
                        }
                }).toList();

                Order newOrder = OrderService.addProducts(order, itemsToProcess);
                Order savedOrder = orderRepo.update(newOrder);

                return new AddItemOrderOutput(orderReadModel.toReadModel(savedOrder));
        }
}