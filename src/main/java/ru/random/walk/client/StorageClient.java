package ru.random.walk.client;

import java.io.File;
import java.net.URL;

public interface StorageClient {
    URL uploadAndGetUrl(File file, String key);

    URL getUrl(String key);
}
