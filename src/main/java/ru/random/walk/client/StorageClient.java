package ru.random.walk.client;

import java.io.File;

public interface StorageClient {
    String uploadAndGetUrl(File file, String key);

    File get(String key);
}
