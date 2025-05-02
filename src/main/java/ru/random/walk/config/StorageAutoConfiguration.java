package ru.random.walk.config;

import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import ru.random.walk.client.StorageClient;
import ru.random.walk.client.impl.StorageClientImpl;

@AutoConfiguration
@EnableConfigurationProperties(StorageProperties.class)
public class StorageAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public StorageProperties storageProperties(StorageProperties properties) {
        return properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public StorageClient storageClient(AmazonS3Client s3Client, StorageProperties properties) {
        return new StorageClientImpl(s3Client, properties);
    }
}
