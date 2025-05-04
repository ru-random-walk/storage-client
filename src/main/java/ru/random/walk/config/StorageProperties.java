package ru.random.walk.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage")
public record StorageProperties(
        String bucketName,
        String accessKey,
        String secretKey,
        String region,
        String endpoint,
        Integer temporaryUrlTtlInMinutes,
        String servicePath
) {
}
