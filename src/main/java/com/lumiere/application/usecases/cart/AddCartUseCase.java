package com.lumiere.application.usecases.cart;

import com.lumiere.application.dtos.cart.command.add.AddCartInput;
import com.lumiere.application.dtos.cart.command.add.AddCartOuput;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.interfaces.cart.IAddCartUseCase;
import com.lumiere.application.mappers.cart.CartItemUseCaseMapper;
import com.lumiere.application.mappers.cart.CartReadModelMapper;
import com.lumiere.application.services.ProductCacheService;
import com.lumiere.application.services.ItemMappingService;
import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.readmodels.CartReadModel;
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
        private final CartItemUseCaseMapper cartMapper;
        private final ProductCacheService productCacheService;
        private final ItemMappingService itemMappingService;
        private final CartReadModelMapper cartReadModel;

        public AddCartUseCase(
                        UserRepository userRepo,
                        CartRepository cartRepo,
                        CartItemUseCaseMapper cartMapper,
                        ProductCacheService productCacheService,
                        ItemMappingService itemMappingService, CartReadModelMapper cartReadModel) {

                this.userRepo = userRepo;
                this.cartRepo = cartRepo;
                this.cartMapper = cartMapper;
                this.productCacheService = productCacheService;
                this.itemMappingService = itemMappingService;
                this.cartReadModel = cartReadModel;
        }

        @Override
        @Transactional
        public AddCartOuput execute(AddCartInput input) {
                User user = userRepo.findUserByAuthId(input.authId())
                                .orElseThrow(UserNotFoundException::new);

                Optional<Cart> existingCartOptional = cartRepo.findCartByUserId(user.getId());

                Set<UUID> productIds = input.requestData().items().stream()
                                .map(item -> item.getProductId())
                                .collect(Collectors.toSet());

                existingCartOptional.ifPresent(existingCart -> {
                        existingCart.getItems().stream()
                                        .map(CartItem::getProductId)
                                        .forEach(productIds::add);
                });

                Map<UUID, Product> productCache = productCacheService.loadProductCache(productIds);

                Cart currentCart = existingCartOptional.orElseGet(() -> CartService.createCart(user));

                List<CartItem> cartItems = itemMappingService.mapItemsToDomainVO(
                                input.requestData().items(),
                                productCache,
                                itemRequestData -> itemRequestData.getProductId(),
                                (product, itemRequestData) -> cartMapper.toCartItem(product, itemRequestData));

                Cart finalCart = CartService.addProducts(currentCart, cartItems);

                finalCart = existingCartOptional.isPresent()
                                ? cartRepo.update(finalCart)
                                : cartRepo.save(finalCart);

                CartReadModel readModel = cartReadModel.toReadModel(finalCart, productCache);

                return new AddCartOuput(readModel);
        }
}