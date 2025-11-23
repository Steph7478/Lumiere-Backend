package com.lumiere.application.usecases.cart;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.cart.command.remove.RemoveCartInput;
import com.lumiere.application.dtos.cart.command.remove.RemoveCartOutput;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.exceptions.cart.CartNotFoundException;
import com.lumiere.application.interfaces.cart.IRemoveCartUseCase;
import com.lumiere.application.mappers.cart.CartReadModelMapper;
import com.lumiere.application.mappers.cart.RemoveCartReadModel;
import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.readmodels.CartReadModel;
import com.lumiere.domain.repositories.CartRepository;
import com.lumiere.domain.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class RemoveCartItemUseCase implements IRemoveCartUseCase {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final RemoveCartReadModel cartMapper;
    private final CartReadModelMapper readModelMapper;

    protected RemoveCartItemUseCase(CartRepository cartRepository, UserRepository userRepository,
            RemoveCartReadModel cartMapper, CartReadModelMapper readModelMapper) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.cartMapper = cartMapper;
        this.readModelMapper = readModelMapper;

    }

    @Override
    @Transactional
    public RemoveCartOutput execute(RemoveCartInput input) {
        User user = userRepository.findUserByAuthId(input.authId())
                .orElseThrow(UserNotFoundException::new);

        Cart cart = cartRepository.findCartByUserId(user.getId()).orElseThrow(CartNotFoundException::new);

        Cart updatedCart = cartMapper.removeProduct(cart, input.requestData());

        cartRepository.update(updatedCart);

        CartReadModel finalCart = readModelMapper.toReadModel(updatedCart);

        return new RemoveCartOutput(finalCart);
    }

}
