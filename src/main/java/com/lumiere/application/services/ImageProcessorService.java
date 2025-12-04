package com.lumiere.application.services;

import java.io.*;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lumiere.infrastructure.storage.S3StorageService;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ImageProcessorService {

        private static final Set<String> ALLOWED_FORMATS = Set.of("png", "jpg", "jpeg", "webp");

        public record OptimizedImage(InputStream stream, long contentLength, String contentType) {
        }

        private OptimizedImage optimize(InputStream input, String format) throws IOException {
                ByteArrayOutputStream out = new ByteArrayOutputStream();

                Thumbnails.of(input)
                                .width(1200)
                                .outputFormat(format)
                                .outputQuality(0.85)
                                .toOutputStream(out);

                byte[] bytes = out.toByteArray();
                return new OptimizedImage(new ByteArrayInputStream(bytes), bytes.length, "image/" + format);
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

                        if (!ALLOWED_FORMATS.contains(format.toLowerCase())) {
                                format = "jpeg";
                        }

                        OptimizedImage optimized = optimize(file.getInputStream(), format);

                        String key = "products/%s/photo.%s".formatted(productId, format);

                        storage.uploadFile(
                                        optimized.stream(),
                                        optimized.contentLength(),
                                        key,
                                        optimized.contentType(),
                                        bucket);

                        return "https://%s.s3.amazonaws.com/%s".formatted(bucket, key);

                } catch (IOException e) {
                        throw new RuntimeException(
                                        "Image processing or upload failed for product: " + productName, e);
                }
        }
}
