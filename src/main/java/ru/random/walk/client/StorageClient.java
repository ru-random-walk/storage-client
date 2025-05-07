package ru.random.walk.client;

import com.amazonaws.services.s3.model.ObjectMetadata;
import ru.random.walk.model.FileExtension;

import java.io.InputStream;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public interface StorageClient {
    /**
     * Upload input stream file with raw metadata and explicit key
     *
     * @param input    input file stream
     * @param metadata raw file metadata that can be saved in storage
     * @param key      path to the file in the storage
     * @return temporary url for input file
     */
    String uploadAndGetUrl(InputStream input, ObjectMetadata metadata, String key);

    /**
     * Get temporary url for uploaded file by raw explicit key
     *
     * @param key path to the file in the storage
     * @return temporary url for input file
     */
    String getUrl(String key);

    /**
     * Check file existence
     * @param key path to the file in the storage
     * @return true if file exists, otherwise returns false
     */
    boolean doesFileExist(String key);

    /**
     * Deletes file from storage
     *
     * @param key path to the file in the storage
     */
    void deleteFile(String key);

    /**
     * Upload and get temporary url for downloading file
     *
     * @param input       input file stream
     * @param keysPathMap all traversal folders path that can be executed in finite storage path
     * @param extension   kind of file that can be executed in finite storage path
     * @return temporary url for input file
     */
    default String uploadAndGetUrl(InputStream input, Map<String, String> keysPathMap, FileExtension extension) {
        var keysPath = keysPathMap.entrySet().stream()
                .map(entry -> "%s/%s".formatted(entry.getKey(), entry.getValue()))
                .reduce("%s/%s"::formatted)
                .orElseThrow(() -> new IllegalArgumentException("keysPathMap size must be greater or equals 1!"));
        var key = "%s.%s".formatted(keysPath, extension.name().toLowerCase());
        return uploadAndGetUrl(input, extension.getMetadata(), key);
    }

    /**
     * Upload png file from input stream with traversal keysPathMap
     *
     * @param input       input file stream
     * @param keysPathMap all traversal folders path that can be executed in finite storage path
     * @return temporary url for input file
     */
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

    /**
     * Get temporary url for uploaded file by keysPathMap and extension
     *
     * @param keysPathMap all traversal folders path that can be executed in finite storage path
     * @param extension   kind of file that can be executed in finite storage path
     * @return temporary url for input file
     */
    default String getUrl(Map<String, String> keysPathMap, FileExtension extension) {
        var keysPath = keysPathMap.entrySet().stream()
                .map(entry -> "%s/%s".formatted(entry.getKey(), entry.getValue()))
                .reduce("%s/%s"::formatted)
                .orElseThrow(() -> new IllegalArgumentException("keysPathMap size must be greater or equals 1!"));
        return getUrl("%s.%s".formatted(keysPath, extension.name().toLowerCase()));
    }

    /**
     * Get temporary url for uploaded png file by keysPathMap
     *
     * @param keysPathMap all traversal folders path that can be executed in finite storage path
     * @return temporary url for input file
     */
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
