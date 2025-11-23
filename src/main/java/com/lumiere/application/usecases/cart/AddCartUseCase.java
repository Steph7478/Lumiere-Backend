package com.lumiere.application.usecases.cart;

import com.lumiere.application.dtos.cart.command.add.AddCartInput;
import com.lumiere.application.dtos.cart.command.add.AddCartOuput;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.interfaces.cart.IAddCartUseCase;
import com.lumiere.application.mappers.cart.AddCartReadModel;
import com.lumiere.application.mappers.cart.CartReadModelMapper;
import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.readmodels.CartReadModel;
import com.lumiere.domain.repositories.CartRepository;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.domain.services.CartService;

import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class AddCartUseCase implements IAddCartUseCase {

        private final UserRepository userRepo;
        private final CartRepository cartRepo;
        private final AddCartReadModel cartMapper;
        private final CartReadModelMapper readModelMapper;

        public AddCartUseCase(UserRepository userRepo, CartRepository cartRepo,
                        AddCartReadModel cartReadModelMapper, CartReadModelMapper readModelMapper) {
                this.userRepo = userRepo;
                this.cartRepo = cartRepo;
                this.cartMapper = cartReadModelMapper;
                this.readModelMapper = readModelMapper;
        }

        @Override
        @Transactional
        public AddCartOuput execute(AddCartInput input) {
                User user = userRepo.findUserByAuthId(input.authId())
                                .orElseThrow(UserNotFoundException::new);

                Optional<Cart> existingCartOptional = cartRepo.findCartByUserId(user.getId());
                Cart currentCart = existingCartOptional.orElseGet(() -> CartService.createCart(user));

                Cart finalCart = cartMapper.addProducts(currentCart, input.requestData());

                finalCart = existingCartOptional.isPresent()
                                ? cartRepo.update(finalCart)
                                : cartRepo.save(finalCart);

                CartReadModel cartReadModel = readModelMapper.toReadModel(finalCart);

                return new AddCartOuput(cartReadModel);
        }
}