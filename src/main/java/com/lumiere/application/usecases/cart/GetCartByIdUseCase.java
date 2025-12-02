package com.lumiere.application.usecases.cart;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.cart.query.GetCartByIdInput;
import com.lumiere.application.dtos.cart.query.GetCartByIdOutput;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.exceptions.cart.CartNotFoundException;
import com.lumiere.application.interfaces.cart.IGetCartByIdUseCase;
import com.lumiere.application.mappers.cart.CartReadModelMapper;
import com.lumiere.application.services.ProductCacheService;
import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.readmodels.CartReadModel;
import com.lumiere.domain.readmodels.ProductDetailReadModel;
import com.lumiere.domain.repositories.CartRepository;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.domain.vo.CartItem;

import jakarta.transaction.Transactional;

@Service
public class GetCartByIdUseCase implements IGetCartByIdUseCase {

    private final CartRepository cartRepo;
    private final UserRepository userRepo;
    private final ProductCacheService productCacheService;
    private final CartReadModelMapper cartReadModelMapper;

    protected GetCartByIdUseCase(
            CartRepository cartRepo,
            UserRepository userRepo,
            ProductCacheService productCacheService,
            CartReadModelMapper cartReadModelMapper) {
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.productCacheService = productCacheService;
        this.cartReadModelMapper = cartReadModelMapper;
    }

    @Override
    @Transactional
    public GetCartByIdOutput execute(GetCartByIdInput input) {
        User user = userRepo.findUserByAuthId(input.userId()).orElseThrow(UserNotFoundException::new);
        UUID cartId = input.cartId().orElse(null);

        Optional<Cart> cartOptional = cartId != null ? cartRepo.findById(cartId)
                : cartRepo.findCartByUserId(user.getId());

        Cart finalCart = cartOptional.orElseThrow(CartNotFoundException::new);

        Set<UUID> productIds = finalCart.getItems().stream()
                .map(CartItem::getProductId)
                .collect(Collectors.toSet());

        Map<String, ProductDetailReadModel> productCache = productCacheService.loadProductCache(
                productIds.stream()
                        .map(UUID::toString)
                        .collect(Collectors.toSet()));

        CartReadModel readModel = cartReadModelMapper.toReadModel(finalCart, productCache);

        return new GetCartByIdOutput(readModel);
    }
}