package com.lumiere.application.usecases.cart;

import com.lumiere.application.dtos.cart.command.add.AddCartInput;
import com.lumiere.application.dtos.cart.command.add.AddCartOuput;
import com.lumiere.application.dtos.cart.command.add.AddCartRequestData;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.interfaces.cart.IAddCartUseCase;
import com.lumiere.application.mappers.cart.CartReadModelMapper;
import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.readmodels.CartReadModel;
import com.lumiere.domain.repositories.CartRepository;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.domain.services.CartService;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class AddCartUseCase implements IAddCartUseCase {

        private final UserRepository userRepo;
        private final CartRepository cartRepo;
        private final CartReadModelMapper cartMapper;

        public AddCartUseCase(UserRepository userRepo, CartRepository cartRepo,
                        CartReadModelMapper cartReadModelMapper) {
                this.userRepo = userRepo;
                this.cartRepo = cartRepo;
                this.cartMapper = cartReadModelMapper;
        }

        @Override
        public AddCartOuput execute(AddCartInput input) {
                AddCartRequestData reqData = input.requestData();

                User user = userRepo.findUserByAuthId(input.authId())
                                .orElseThrow(UserNotFoundException::new);

                Optional<Cart> existingCartOptional = cartRepo.findCartByUserId(user.getId());
                Cart currentCart = existingCartOptional.orElseGet(() -> CartService.createCart(user));

                Cart finalCart = cartMapper.addProducts(currentCart, reqData);

                finalCart = existingCartOptional.isPresent()
                                ? cartRepo.update(finalCart)
                                : cartRepo.save(finalCart);

                CartReadModel cartReadModel = cartMapper.toDTO(finalCart);

                return new AddCartOuput(cartReadModel);
        }
}