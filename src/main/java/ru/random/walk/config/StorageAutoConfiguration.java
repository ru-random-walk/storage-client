package ru.random.walk.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import ru.random.walk.client.StorageClient;
import ru.random.walk.client.impl.StorageClientImpl;

import java.time.Duration;

@AutoConfiguration
@EnableConfigurationProperties(StorageProperties.class)
public class StorageAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(StorageClient.class)
    public AmazonS3Client s3Client(StorageProperties properties) {
        var credentials = new BasicAWSCredentials(properties.accessKey(), properties.secretKey());
        return (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(properties.endpoint(), properties.region())
                )
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(StorageClient.class)
    public StorageClient storageClient(AmazonS3Client s3Client, StorageProperties properties) {
        Duration temporaryUrlTtl = Duration.ofMinutes(properties.temporaryUrlTtlInMinutes());
        return new StorageClientImpl(s3Client, properties.bucketName(), properties.servicePath(), temporaryUrlTtl);
    }
}
