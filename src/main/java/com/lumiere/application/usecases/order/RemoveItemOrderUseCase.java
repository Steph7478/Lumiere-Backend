package com.lumiere.application.usecases.order;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.order.command.remove.RemoveItemOrderInput;
import com.lumiere.application.dtos.order.command.remove.RemoveItemOutput;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.exceptions.order.OrderNotFoundException;
import com.lumiere.application.interfaces.order.IRemoveItemOrderUseCase;
import com.lumiere.domain.entities.Order;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.enums.StatusEnum.Status;
import com.lumiere.domain.repositories.OrderRepository;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.domain.services.OrderService;
import com.lumiere.domain.vo.OrderItem;

import jakarta.transaction.Transactional;

@Service
public class RemoveItemOrderUseCase implements IRemoveItemOrderUseCase {
    private final UserRepository userRepo;
    private final OrderRepository orderRepo;

    protected RemoveItemOrderUseCase(UserRepository userRepo, OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    @CacheEvict(value = "productJpaList", allEntries = true)
    public RemoveItemOutput execute(RemoveItemOrderInput input) {
        User user = userRepo.findUserByAuthId(input.userId()).orElseThrow(UserNotFoundException::new);

        Order order = orderRepo.findByUserIdAndStatus(user.getId(), Status.IN_PROGRESS)
                .orElseThrow(OrderNotFoundException::new);

        List<UUID> idsToRemove = input.items().productsId();

        List<OrderItem> itemsToRemove = order.getItems().stream()
                .filter(item -> idsToRemove.contains(item.getProductId()))
                .toList();

        Order updatedOrder = OrderService.removeProducts(order, itemsToRemove);

        orderRepo.update(updatedOrder);

        return new RemoveItemOutput();
    }

}
