package ru.random.walk;

import ru.random.walk.client.StorageClient;
import ru.random.walk.config.StorageAutoConfiguration;
import ru.random.walk.config.StorageProperties;
import ru.random.walk.util.PathBuilder;

import java.io.IOException;

public class MainExample {
    public static void main(String[] args) throws IOException {
        var storageClient = initStorageClient();
        storageClient.delete(
                PathBuilder.init()
                        .add("Some pom")
                        .add(PathBuilder.Key.CLUB_ID, "50158648-5320-4769-aa82-7c7677533db9")
                        .build()
        );
    }

    private static StorageClient initStorageClient() {
        var envVars = System.getenv();
        StorageProperties storageProperties = new StorageProperties(
                envVars.get("STORAGE_BUCKET_NAME"),
                envVars.get("STORAGE_ACCESS_KEY"),
                envVars.get("STORAGE_SECRET_KEY"),
                envVars.get("STORAGE_REGION"),
                envVars.get("STORAGE_ENDPOINT"),
                5,
                "test-service-path"
        );
        StorageAutoConfiguration storageAutoConfiguration = new StorageAutoConfiguration();
        var s3Client = storageAutoConfiguration.s3Client(storageProperties);
        return storageAutoConfiguration.storageClient(s3Client, storageProperties);
    }
}
