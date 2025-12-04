package com.lumiere.infrastructure.config.environment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Configuration
public class MinioS3Config {

    @Value("${minio.endpoint}")
    private String endpointUrl;

    @Value("${minio.access.key}")
    private String accessKey;

    @Value("${minio.secret.key}")
    private String secretKey;

    @Value("${minio.region}")
    private String region;

    @Value("${minio.force.path.style}")
    private Boolean forcePathStyle;

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        S3Configuration serviceConfiguration = S3Configuration.builder()
                .pathStyleAccessEnabled(forcePathStyle)
                .build();

        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .endpointOverride(URI.create(endpointUrl))
                .serviceConfiguration(serviceConfiguration)
                .build();
    }
}