package ru.random.walk.client;

import com.amazonaws.services.s3.model.ObjectMetadata;
import ru.random.walk.model.FileExtension;

import java.io.InputStream;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public interface StorageClient {
    String uploadAndGetUrl(InputStream input, ObjectMetadata metadata, String key);

    String getUrl(String key);

    default String uploadAndGetUrl(InputStream input, Map<String, String> keysPathMap, FileExtension extension) {
        var keysPath = keysPathMap.entrySet().stream()
                .map(entry -> "%s/%s".formatted(entry.getKey(), entry.getValue()))
                .reduce("%s/%s"::formatted)
                .orElseThrow(() -> new IllegalArgumentException("keysPathMap size must be greater or equals 1!"));
        var key = "%s.%s".formatted(keysPath, extension.name().toLowerCase());
        return uploadAndGetUrl(input, extension.getMetadata(), key);
    }

    default String uploadPngAndGetUrl(InputStream input, Map<String, UUID> keysPathMap) {
        return uploadAndGetUrl(
                input,
                keysPathMap.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue().toString()
                        )),
                FileExtension.PNG
        );
    }

    default String getUrl(Map<String, String> keysPathMap, FileExtension extension) {
        var keysPath = keysPathMap.entrySet().stream()
                .map(entry -> "%s/%s".formatted(entry.getKey(), entry.getValue()))
                .reduce("%s/%s"::formatted)
                .orElseThrow(() -> new IllegalArgumentException("keysPathMap size must be greater or equals 1!"));
        return getUrl("%s.%s".formatted(keysPath, extension.name().toLowerCase()));
    }

    default String getPngUrl(Map<String, UUID> keysPathMap) {
        return getUrl(
                keysPathMap.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue().toString()
                        )),
                FileExtension.PNG
        );
    }
}
