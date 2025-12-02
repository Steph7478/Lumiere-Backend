package com.lumiere.application.usecases.cart;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.cart.command.remove.RemoveCartInput;
import com.lumiere.application.dtos.cart.command.remove.RemoveCartOutput;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.exceptions.cart.CartNotFoundException;
import com.lumiere.application.interfaces.cart.IRemoveCartUseCase;
import com.lumiere.application.mappers.cart.CartReadModelMapper;
import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.readmodels.CartReadModel;
import com.lumiere.domain.readmodels.ProductDetailReadModel;
import com.lumiere.domain.repositories.CartRepository;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.domain.services.CartService;
import com.lumiere.domain.vo.CartItem;
import com.lumiere.application.services.ProductCacheService;

import jakarta.transaction.Transactional;

@Service
public class RemoveCartItemUseCase implements IRemoveCartUseCase {

        private final CartRepository cartRepository;
        private final UserRepository userRepository;
        private final CartReadModelMapper cartReadModel;
        private final ProductCacheService productCacheService;

        public RemoveCartItemUseCase(
                        CartRepository cartRepository,
                        UserRepository userRepository, CartReadModelMapper cartReadModel,
                        ProductCacheService productCacheService) {
                this.cartRepository = cartRepository;
                this.userRepository = userRepository;
                this.cartReadModel = cartReadModel;
                this.productCacheService = productCacheService;
        }

        @Override
        @Transactional
        public RemoveCartOutput execute(RemoveCartInput input) {

                User user = userRepository.findUserByAuthId(input.authId())
                                .orElseThrow(UserNotFoundException::new);

                Cart cart = cartRepository.findCartByUserId(user.getId())
                                .orElseThrow(CartNotFoundException::new);

                Cart updatedCart = CartService.removeProduct(cart, input.requestData().items());

                cartRepository.update(updatedCart);

                Set<UUID> productIds = updatedCart.getItems().stream()
                                .map(CartItem::getProductId)
                                .collect(Collectors.toSet());

                Map<String, ProductDetailReadModel> productCache = productCacheService
                                .loadProductCache(productIds.stream().map(UUID::toString).collect(Collectors.toSet()));

                CartReadModel readModel = cartReadModel.toReadModel(updatedCart, productCache);

                return new RemoveCartOutput(readModel);
        }
}
