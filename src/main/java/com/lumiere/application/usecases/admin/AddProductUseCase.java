package com.lumiere.application.usecases.admin;

import java.util.List;
import java.util.stream.IntStream;
import java.util.UUID;
import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.admin.command.add.AddProductInput;
import com.lumiere.application.dtos.admin.command.add.AddProductOutput;
import com.lumiere.application.dtos.admin.command.add.AddProductRequestData;
import com.lumiere.application.interfaces.admin.IAddProductUseCase;
import com.lumiere.application.mappers.admin.AddProductMapper;
import com.lumiere.application.services.ImageProcessorService;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.readmodels.ProductDetailReadModel;
import com.lumiere.domain.repositories.NoSqlRepository;
import com.lumiere.domain.repositories.ProductRepository;
import com.lumiere.domain.services.ProductCategoryService;
import com.lumiere.domain.services.ProductService;
import com.lumiere.domain.vo.Money;
import com.lumiere.domain.vo.Stock;
import com.lumiere.infrastructure.storage.S3StorageService;
import com.lumiere.shared.annotations.validators.RequireAdmin;

import jakarta.transaction.Transactional;

@Service
public class AddProductUseCase implements IAddProductUseCase {

        private static final String PRODUCT_BUCKET_NAME = "product-pictures";

        private final ProductRepository productRepository;
        private final NoSqlRepository<ProductCategory> categoryRepository;
        private final AddProductMapper mapper;
        private final S3StorageService storageService;
        private final ImageProcessorService imageProcessorService;

        public AddProductUseCase(
                        ProductRepository productRepository,
                        NoSqlRepository<ProductCategory> categoryRepository,
                        AddProductMapper mapper,
                        S3StorageService storageService,
                        ImageProcessorService imageProcessorService) {

                this.productRepository = productRepository;
                this.categoryRepository = categoryRepository;
                this.mapper = mapper;
                this.storageService = storageService;
                this.imageProcessorService = imageProcessorService;
        }

        @Override
        @Transactional
        @RequireAdmin
        public AddProductOutput execute(AddProductInput input) {

                List<Product> products = IntStream.range(0, input.items().size())
                                .mapToObj(i -> {
                                        AddProductRequestData item = input.items().get(i);

                                        String imageUrl = null;
                                        UUID productId = UUID.randomUUID();

                                        if (item.imageFile() != null) {
                                                imageUrl = imageProcessorService.processAndUpload(
                                                                productId,
                                                                item.imageFile(),
                                                                item.name(),
                                                                storageService,
                                                                PRODUCT_BUCKET_NAME);
                                        }

                                        Product product = ProductService.create(
                                                        productId,
                                                        item.name(),
                                                        item.description(),
                                                        new Money(item.priceAmount(), item.currency()),
                                                        new Stock(item.stockQuantity()),
                                                        imageUrl);

                                        return productRepository.save(product);
                                })
                                .toList();

                IntStream.range(0, products.size())
                                .mapToObj(i -> ProductCategoryService.createProductCategory(
                                                products.get(i).getId(),
                                                input.items().get(i).category(),
                                                input.items().get(i).subcategory()))
                                .forEach(categoryRepository::save);

                List<ProductDetailReadModel> readModels = IntStream.range(0, products.size())
                                .mapToObj(i -> mapper.toReadModel(products.get(i), input.items().get(i)))
                                .toList();

                return mapper.toOutput(readModels);
        }
}