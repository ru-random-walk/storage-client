package ru.random.walk.client.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.random.walk.client.StorageClient;
import ru.random.walk.model.FileType;

import java.io.File;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Slf4j
@AllArgsConstructor
public class StorageClientImpl implements StorageClient {
    private final AmazonS3Client s3Client;
    private final String bucketName;
    private final String servicePath;
    private final Duration temporaryUrlTtl;

    @Override
    public String uploadAndGetUrl(File input, String key) {
        var fileKey = getFileServiceKey(key);
        s3Client.putObject(bucketName, fileKey, input);
        return getUrl(key);
    }

    @Override
    public String uploadAndGetUrl(InputStream input, String key) {
        var fileKey = getFileServiceKey(key);
        var emptyMetadata = new ObjectMetadata();
        s3Client.putObject(bucketName, fileKey, input, emptyMetadata);
        return getUrl(key);
    }

    @Override
    public String uploadAndGetUrl(InputStream input, String key, FileType fileType) {
        var fileKey = getFileServiceKey(key);
        var metadata = fileType.getMetadata();
        s3Client.putObject(bucketName, fileKey, input, metadata);
        return getUrl(key);
    }

    @Override
    public String getUrl(String key) {
        var fileKey = getFileServiceKey(key);
        Date expiration = Date.from(Instant.now().plus(temporaryUrlTtl));
        return s3Client.generatePresignedUrl(bucketName, fileKey, expiration).toString();
    }

    @Override
    public void delete(String key) {
        var fileKey = getFileServiceKey(key);
        s3Client.deleteObject(bucketName, fileKey);
    }

    @Override
    public boolean exist(String key) {
        var fileKey = getFileServiceKey(key);
        return s3Client.doesObjectExist(bucketName, fileKey);
    }

    private String getFileServiceKey(String key) {
        return "%s/%s".formatted(servicePath, key);
    }
}
