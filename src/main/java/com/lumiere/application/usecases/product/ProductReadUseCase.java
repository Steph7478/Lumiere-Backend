package com.lumiere.application.usecases.product;

import com.lumiere.application.dtos.product.query.ProductDetailsOutput;
import com.lumiere.application.dtos.product.query.ProductFindAllCriteria;
import com.lumiere.application.dtos.product.query.ProductSearchCriteria;
import com.lumiere.application.ports.ProductDetailReadPort;
import com.lumiere.domain.readmodels.ProductDetailReadModel;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProductReadUseCase {

    private final ProductDetailReadPort productDetailReadPort;

    public ProductReadUseCase(ProductDetailReadPort productDetailReadPort) {
        this.productDetailReadPort = productDetailReadPort;
    }

    public Optional<ProductDetailReadModel> findDetailById(UUID id) {
        return productDetailReadPort.findProductDetailById(id);
    }

    public ProductDetailsOutput findByCriteria(ProductSearchCriteria criteria) {
        Page<ProductDetailReadModel> productPage = productDetailReadPort.findProductsByCriteria(criteria);

        return ProductDetailsOutput.fromPage(productPage);
    }

    public ProductDetailsOutput findAllProducts(ProductFindAllCriteria criteria) {
        Page<ProductDetailReadModel> productPage = productDetailReadPort.findAllProducts(criteria);

        return ProductDetailsOutput.fromPage(productPage);
    }
}