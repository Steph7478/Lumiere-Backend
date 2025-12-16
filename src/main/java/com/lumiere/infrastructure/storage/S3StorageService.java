package com.lumiere.infrastructure.storage;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3StorageService {

    private final S3Client s3Client;

    public S3StorageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(
            InputStream fileStream,
            long contentLength,
            String keyName,
            String contentType,
            String bucketName) {

        createBucketIfNotExists(bucketName);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .contentLength(contentLength)
                .contentType(contentType)
                .build();

        PutObjectResponse response = s3Client.putObject(
                putObjectRequest,
                RequestBody.fromInputStream(fileStream, contentLength));

        return response.eTag();
    }

    private void createBucketIfNotExists(String bucketName) {
        boolean exists = s3Client
                .listBuckets()
                .buckets()
                .stream()
                .anyMatch(b -> b.name().equals(bucketName));

        if (!exists) {
            s3Client.createBucket(
                    CreateBucketRequest.builder()
                            .bucket(bucketName)
                            .build());
        }
    }

    public boolean deleteObjectsByKeys(String bucketName, List<String> keys) {
        if (keys.isEmpty())
            return false;

        List<ObjectIdentifier> objects = keys.stream()
                .map(key -> ObjectIdentifier.builder().key(key).build())
                .collect(Collectors.toList());

        Delete delete = Delete.builder()
                .objects(objects)
                .build();

        DeleteObjectsRequest deleteObjectsRequest = DeleteObjectsRequest.builder()
                .bucket(bucketName)
                .delete(delete)
                .build();

        DeleteObjectsResponse response = s3Client.deleteObjects(deleteObjectsRequest);

        return response.errors().isEmpty();
    }
}
