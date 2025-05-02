package ru.random.walk.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@AllArgsConstructor
public class StorageConfiguration {
    private final StorageProperties properties;

    @Bean
    public BasicAWSCredentials credentials() {
        return new BasicAWSCredentials(properties.accessKey(), properties.secretKey());
    }

    @Bean
    public AmazonS3Client s3Client() {
        return (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials()))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(properties.endpoint(), properties.region())
                )
                .build();
    }
}
