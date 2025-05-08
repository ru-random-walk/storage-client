package ru.random.walk;

import ru.random.walk.client.StorageClient;
import ru.random.walk.config.StorageAutoConfiguration;
import ru.random.walk.config.StorageProperties;
import ru.random.walk.model.FileType;
import ru.random.walk.util.PathBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

public class MainExample {
    public static void main(String[] args) throws IOException {
        var storageClient = initStorageClient();
        File file = new File("pic.jpg");
        try (var fileInputStream = new FileInputStream(file)) {
            var url = storageClient.uploadAndGetUrl(
                    fileInputStream,
                    PathBuilder.init()
                            .add("Some pom")
                            .add(PathBuilder.Key.CLUB_ID, UUID.randomUUID())
                            .build(),
                    FileType.JPG
            );
            System.out.printf("Url = %s\n", url);
        }
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
