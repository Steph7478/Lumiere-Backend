package com.lumiere.application.usecases.cart;

import com.lumiere.application.dtos.cart.command.add.AddCartInput;
import com.lumiere.application.dtos.cart.command.add.AddCartOuput;
import com.lumiere.application.dtos.cart.command.add.AddCartRequestData;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.interfaces.cart.IAddCartUseCase;
import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.readmodels.CartItemReadModel;
import com.lumiere.domain.repositories.CartRepository;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.domain.services.CartService;
import com.lumiere.infrastructure.mappers.CartItemMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class AddCartUseCase implements IAddCartUseCase {

    private final UserRepository userRepo;
    private final CartRepository cartRepo;
    private final CartItemMapper cartItemMapper;

    public AddCartUseCase(UserRepository userRepo, CartRepository cartRepo, CartItemMapper cartItemMapper) {
        this.userRepo = userRepo;
        this.cartRepo = cartRepo;
        this.cartItemMapper = cartItemMapper;
    }

    @Override
    public AddCartOuput execute(AddCartInput input) {
        AddCartRequestData reqData = input.requestData();

        User user = userRepo.findUserByAuthId(input.authId())
                .orElseThrow(UserNotFoundException::new);

        Optional<Cart> existingCartOptional = cartRepo.findCartByUserId(user.getId());
        Cart currentCart = existingCartOptional.orElseGet(() -> CartService.createCart(user));

        Cart finalCart = CartService.addProducts(
                currentCart,
                reqData.items(),
                reqData.coupon());

        if (existingCartOptional.isPresent()) {
            cartRepo.update(finalCart);
        } else {
            cartRepo.save(finalCart);
        }

        List<CartItemReadModel> readModels = finalCart.getItems().stream()
                .map(cartItemMapper::toReadModel)
                .collect(Collectors.toList());

        return new AddCartOuput(
                finalCart.getId(),
                readModels,
                finalCart.getCreatedAt(),
                finalCart.getUpdatedAt(),
                finalCart.getCoupon().orElse(null));
    }
}