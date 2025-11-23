package com.lumiere.application.usecases.cart;

import org.springframework.stereotype.Service;
import com.lumiere.application.dtos.cart.command.delete.DeleteCartInput;
import com.lumiere.application.dtos.cart.command.delete.DeleteCartOutput;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.exceptions.cart.CartNotFoundException;
import com.lumiere.application.interfaces.cart.IDeleteCartUseCase;
import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.repositories.CartRepository;
import com.lumiere.domain.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class DeleteCartUseCase implements IDeleteCartUseCase {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public DeleteCartUseCase(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public DeleteCartOutput execute(DeleteCartInput request) {
        User user = userRepository.findUserByAuthId(request.id()).orElseThrow(UserNotFoundException::new);
        Cart cart = cartRepository.findCartByUserId(user.getId())
                .orElseThrow(CartNotFoundException::new);

        cartRepository.deleteById(cart.getId());
        return new DeleteCartOutput();
    }
}