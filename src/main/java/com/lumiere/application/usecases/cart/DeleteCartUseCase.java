package com.lumiere.application.usecases.cart;

import org.springframework.stereotype.Service;
import com.lumiere.application.dtos.cart.command.delete.DeleteCartInput;
import com.lumiere.application.dtos.cart.command.delete.DeleteCartOutput;
import com.lumiere.application.exceptions.cart.CartNotFoundException;
import com.lumiere.application.interfaces.cart.IDeleteCartUseCase;
import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.repositories.CartRepository;

import jakarta.transaction.Transactional;

@Service
public class DeleteCartUseCase implements IDeleteCartUseCase {
    private final CartRepository cartRepository;

    public DeleteCartUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional
    public DeleteCartOutput execute(DeleteCartInput request) {
        Cart cart = cartRepository.findById(request.cartId())
                .orElseThrow(CartNotFoundException::new);

        cartRepository.deleteById(cart.getId());
        return new DeleteCartOutput();
    }
}