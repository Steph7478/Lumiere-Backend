package com.lumiere.application.usecases.cart;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.cart.query.GetCartByIdInput;
import com.lumiere.application.dtos.cart.query.GetCartByIdOutput;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.exceptions.cart.CartNotFoundException;
import com.lumiere.application.interfaces.cart.IGetCartByIdUseCase;
import com.lumiere.application.mappers.cart.CartReadModelMapper;
import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.readmodels.CartReadModel;
import com.lumiere.domain.repositories.CartRepository;
import com.lumiere.domain.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class GetCartByIdUseCase implements IGetCartByIdUseCase {

    private final CartRepository cartRepo;
    private final CartReadModelMapper readModelMapper;
    private final UserRepository userRepo;

    protected GetCartByIdUseCase(CartRepository cartRepo, CartReadModelMapper readModelMapper,
            UserRepository userRepo) {
        this.cartRepo = cartRepo;
        this.readModelMapper = readModelMapper;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public GetCartByIdOutput execute(GetCartByIdInput input) {
        User user = userRepo.findUserByAuthId(input.userId()).orElseThrow(UserNotFoundException::new);
        UUID cartId = input.cartId().orElse(null);

        Optional<Cart> cart = cartId != null ? cartRepo.findById(cartId)
                : cartRepo.findCartByUserId(user.getId());

        CartReadModel finalCart = readModelMapper.toReadModel(cart.orElseThrow(CartNotFoundException::new));

        return new GetCartByIdOutput(finalCart);
    }

}