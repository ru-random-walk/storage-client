package ru.random.walk.client;

import ru.random.walk.model.FileExtension;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public interface StorageClient {
    URL uploadAndGetUrl(File file, String key);

    URL getUrl(String key);

    default URL uploadAndGetUrl(File file, Map<String, String> keysPathMap, FileExtension extension) {
        var keysPath = keysPathMap.entrySet().stream()
                .map(entry -> "%s/%s".formatted(entry.getKey(), entry.getValue()))
                .reduce("%s/%s"::formatted)
                .orElseThrow(() -> new IllegalArgumentException("keysPathMap size must be greater or equals 1!"));
        return uploadAndGetUrl(file, "%s.%s".formatted(keysPath, extension.name().toLowerCase()));
    }

    default URL uploadPngAndGetUrl(File file, Map<String, UUID> keysPathMap) {
        return uploadAndGetUrl(
                file,
                keysPathMap.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue().toString()
                        )),
                FileExtension.PNG
        );
    }
}
