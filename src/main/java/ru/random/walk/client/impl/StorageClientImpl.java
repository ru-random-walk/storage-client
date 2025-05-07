package ru.random.walk.client.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.random.walk.client.StorageClient;
import ru.random.walk.config.StorageProperties;

import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Slf4j
@AllArgsConstructor
public class StorageClientImpl implements StorageClient {
    private final AmazonS3Client s3Client;
    private final StorageProperties properties;

    @Override
    public String uploadAndGetUrl(InputStream input, ObjectMetadata metadata, String key) {
        var bucketName = properties.bucketName();
        var keyObjectPath = getKeyObjectPath(key);
        s3Client.putObject(bucketName, keyObjectPath, input, metadata);
        return getUrl(key);
    }

    @Override
    public String getUrl(String key) {
        var bucketName = properties.bucketName();
        var expiration = getExpiration();
        var keyObjectPath = getKeyObjectPath(key);
        return s3Client.generatePresignedUrl(bucketName, keyObjectPath, expiration).toString();
    }

    @Override
    public boolean doesFileExist(String key) {
        return s3Client.doesObjectExist(properties.bucketName(), key);
    }

    @Override
    public void deleteFile(String key) {
        s3Client.deleteObject(properties.bucketName(), key);
    }

    private String getKeyObjectPath(String key) {
        return "%s/%s".formatted(
                properties.servicePath().toLowerCase(),
                key
        );
    }

    private Date getExpiration() {
        Instant now = Instant.now();
        Duration ttl = Duration.ofMinutes(properties.temporaryUrlTtlInMinutes());
        return Date.from(now.plus(ttl));
    }
}
