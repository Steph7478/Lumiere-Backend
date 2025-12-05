package com.lumiere.application.services;

import java.io.InputStream;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lumiere.infrastructure.storage.S3StorageService;

@Service
public class ImageProcessorService {

        private static final Set<String> ALLOWED_FORMATS = Set.of("png", "jpg", "jpeg", "webp");

        @Value("${minio.endpoint}")
        private String minioEndpoint;

        private final ImageOptimizerService optimizer;

        public ImageProcessorService(ImageOptimizerService optimizer) {
                this.optimizer = optimizer;
        }

        public record OptimizedImage(InputStream stream, long contentLength, String contentType) {
        }

        public String processAndUpload(
                        UUID productId,
                        MultipartFile file,
                        String productName,
                        S3StorageService storage,
                        String bucket) {

                try {
                        String contentType = file.getContentType();
                        String format = (contentType != null && contentType.startsWith("image/"))
                                        ? contentType.substring(6)
                                        : "jpeg";

                        if (!ALLOWED_FORMATS.contains(format.toLowerCase()))
                                format = "jpeg";

                        OptimizedImage optimized = optimizer.optimize(file.getInputStream(), format);

                        String key = "%s.photo.%s".formatted(productId, format);

                        storage.uploadFile(
                                        optimized.stream(),
                                        optimized.contentLength(),
                                        key,
                                        optimized.contentType(),
                                        bucket);

                        return "%s/%s/%s".formatted(minioEndpoint, bucket, key);

                } catch (IOException e) {
                        throw new RuntimeException("Error to process the image for product " + productId);
                }
        }
}
