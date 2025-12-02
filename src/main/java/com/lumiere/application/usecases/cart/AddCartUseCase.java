package com.lumiere.application.usecases.cart;

import com.lumiere.application.dtos.cart.command.add.AddCartInput;
import com.lumiere.application.dtos.cart.command.add.AddCartOuput;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.interfaces.cart.IAddCartUseCase;
import com.lumiere.application.mappers.cart.CartReadModelMapper;
import com.lumiere.application.services.ProductCacheService;
import com.lumiere.application.services.ItemMappingService;
import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.readmodels.CartReadModel;
import com.lumiere.domain.readmodels.ProductDetailReadModel;
import com.lumiere.domain.repositories.CartRepository;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.domain.services.CartService;
import com.lumiere.domain.vo.CartItem;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class AddCartUseCase implements IAddCartUseCase {

        private final UserRepository userRepo;
        private final CartRepository cartRepo;
        private final ProductCacheService productCacheService;
        private final CartReadModelMapper cartReadModel;

        public AddCartUseCase(
                        UserRepository userRepo,
                        CartRepository cartRepo,
                        ProductCacheService productCacheService,
                        ItemMappingService itemMappingService, CartReadModelMapper cartReadModel) {

                this.userRepo = userRepo;
                this.cartRepo = cartRepo;
                this.productCacheService = productCacheService;
                this.cartReadModel = cartReadModel;
        }

        @Override
        @Transactional
        public AddCartOuput execute(AddCartInput input) {
                User user = userRepo.findUserByAuthId(input.authId())
                                .orElseThrow(UserNotFoundException::new);

                Optional<Cart> existingCartOptional = cartRepo.findCartByUserId(user.getId());

                Set<UUID> productIds = input.requestData().items().stream()
                                .map(item -> item.productId())
                                .collect(Collectors.toSet());

                existingCartOptional.ifPresent(existingCart -> {
                        existingCart.getItems().stream()
                                        .map(CartItem::getProductId)
                                        .forEach(productIds::add);
                });

                Map<String, ProductDetailReadModel> productCache = productCacheService
                                .loadProductCache(productIds.stream()
                                                .map(UUID::toString)
                                                .collect(Collectors.toSet()));

                Cart currentCart = existingCartOptional.orElseGet(() -> CartService.createCart(user));

                List<CartItem> cartItems = input.requestData().items()
                                .stream()
                                .map(item -> {
                                        String productId = item.productId().toString();
                                        ProductDetailReadModel product = productCache.get(productId);
                                        return new CartItem(
                                                        product.id(),
                                                        item.quantity());
                                })
                                .collect(Collectors.toList());

                Cart finalCart = CartService.addProducts(currentCart, cartItems);

                finalCart = existingCartOptional.isPresent()
                                ? cartRepo.update(finalCart)
                                : cartRepo.save(finalCart);

                CartReadModel readModel = cartReadModel.toReadModel(finalCart, productCache);

                return new AddCartOuput(readModel);
        }
}