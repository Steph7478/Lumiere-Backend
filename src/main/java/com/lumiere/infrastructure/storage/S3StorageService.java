package com.lumiere.infrastructure.storage;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

@Service
public class S3StorageService {

    private final S3Client s3Client;
    private final String bucketName = "product-pictures";

    public S3StorageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @PostConstruct
    public void init() {
        createBucketIfNotExists();
    }

    private void createBucketIfNotExists() {
        ListBucketsResponse buckets = s3Client.listBuckets();

        boolean exists = buckets.buckets().stream()
                .anyMatch(b -> b.name().equals(bucketName));

        if (!exists)
            s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());

    }

    public String startMultipartUpload(String keyName) {
        CreateMultipartUploadRequest createRequest = CreateMultipartUploadRequest.builder().bucket(bucketName)
                .key(keyName).build();

        CreateMultipartUploadResponse createResponse = s3Client.createMultipartUpload(createRequest);

        return createResponse.uploadId();
    }
}
