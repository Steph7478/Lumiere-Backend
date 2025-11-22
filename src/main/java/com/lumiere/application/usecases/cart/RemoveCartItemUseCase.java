package com.lumiere.application.usecases.cart;

import com.lumiere.application.dtos.cart.command.remove.RemoveCartInput;
import com.lumiere.application.dtos.cart.command.remove.RemoveCartOutput;
import com.lumiere.application.dtos.cart.command.remove.RemoveMultipleItemsRequestData;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.exceptions.cart.CartNotFoundException;
import com.lumiere.application.interfaces.cart.IRemoveCartUseCase;
import com.lumiere.application.mappers.cart.CartReadModelMapper;
import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.readmodels.CartReadModel;
import com.lumiere.domain.repositories.CartRepository;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.domain.services.CartService;

import jakarta.transaction.Transactional;

public class RemoveCartItemUseCase implements IRemoveCartUseCase {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartReadModelMapper cartMapper;

    protected RemoveCartItemUseCase(CartRepository cartRepository, UserRepository userRepository,
            CartReadModelMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    @Transactional
    public RemoveCartOutput execute(RemoveCartInput input) {
        RemoveMultipleItemsRequestData reqData = input.requestData();

        User user = userRepository.findUserByAuthId(input.authId())
                .orElseThrow(UserNotFoundException::new);

        Cart cart = cartRepository.findCartByUserId(user.getId()).orElseThrow(CartNotFoundException::new);

        Cart updatedCart = CartService.removeProduct(cart, reqData.productIds());

        cartRepository.update(updatedCart);

        CartReadModel finalCart = cartMapper.toDTO(updatedCart);

        return new RemoveCartOutput(finalCart);
    }

}
