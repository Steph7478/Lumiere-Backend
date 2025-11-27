package com.lumiere.application.usecases.cart;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.cart.command.remove.RemoveCartInput;
import com.lumiere.application.dtos.cart.command.remove.RemoveCartOutput;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.exceptions.cart.CartNotFoundException;
import com.lumiere.application.interfaces.cart.IRemoveCartUseCase;
import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.readmodels.CartReadModel;
import com.lumiere.domain.repositories.CartRepository;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.infrastructure.mappers.CartMapper;
import com.lumiere.domain.services.CartService;

import jakarta.transaction.Transactional;

@Service
public class RemoveCartItemUseCase implements IRemoveCartUseCase {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartMapper readModelMapper;

    protected RemoveCartItemUseCase(CartRepository cartRepository, UserRepository userRepository,
            CartMapper readModelMapper) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.readModelMapper = readModelMapper;

    }

    @Override
    @Transactional
    public RemoveCartOutput execute(RemoveCartInput input) {
        User user = userRepository.findUserByAuthId(input.authId())
                .orElseThrow(UserNotFoundException::new);

        Cart cart = cartRepository.findCartByUserId(user.getId()).orElseThrow(CartNotFoundException::new);

        Cart updatedCart = CartService.removeProduct(cart, input.requestData().items());

        cartRepository.update(updatedCart);

        CartReadModel finalCart = readModelMapper.toReadModel(updatedCart);

        return new RemoveCartOutput(finalCart);
    }

}
