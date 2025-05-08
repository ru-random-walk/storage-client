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
    public String uploadAndGetUrl(File input, String explicitKey) {
        var fileKey = getFileKey(explicitKey);
        s3Client.putObject(bucketName, fileKey, input);
        return getUrl(explicitKey);
    }

    @Override
    public String uploadAndGetUrl(InputStream input, String explicitKey) {
        var fileKey = getFileKey(explicitKey);
        var emptyMetadata = new ObjectMetadata();
        s3Client.putObject(bucketName, fileKey, input, emptyMetadata);
        return getUrl(explicitKey);
    }

    @Override
    public String uploadAndGetUrl(InputStream input, String explicitKey, FileType fileType) {
        var fileKey = getFileKey(explicitKey);
        var metadata = fileType.getMetadata();
        s3Client.putObject(bucketName, fileKey, input, metadata);
        return getUrl(explicitKey);
    }

    @Override
    public String getUrl(String explicitKey) {
        var fileKey = getFileKey(explicitKey);
        Date expiration = Date.from(Instant.now().plus(temporaryUrlTtl));
        return s3Client.generatePresignedUrl(bucketName, fileKey, expiration).toString();
    }

    @Override
    public void delete(String explicitKey) {
        var fileKey = getFileKey(explicitKey);
        s3Client.deleteObject(bucketName, fileKey);
    }

    @Override
    public boolean exist(String explicitKey) {
        var fileKey = getFileKey(explicitKey);
        return s3Client.doesObjectExist(bucketName, fileKey);
    }

    private String getFileKey(String key) {
        return "%s/%s".formatted(servicePath, key);
    }
}
