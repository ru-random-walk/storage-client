package ru.random.walk.client;

import ru.random.walk.model.FileType;

import java.io.File;
import java.io.InputStream;

public interface StorageClient {
    /**
     * Upload input stream file with explicit key
     *
     * @param key path to the file in the storage
     * @return temporary url for input file
     */
    String uploadAndGetUrl(File input, String key);

    /**
     * Upload input stream file with explicit key
     *
     * @param key path to the file in the storage
     * @return temporary url for input file
     */
    String uploadAndGetUrl(InputStream input, String key);

    /**
     * Upload input stream file with type and explicit key
     *
     * @param key path to the file in the storage
     * @return temporary url for input file
     */
    String uploadAndGetUrl(InputStream input, String key, FileType fileType);

    /**
     * Get temporary url for uploaded file by raw explicit key
     *
     * @param key path to the file in the storage
     * @return temporary url for input file
     */
    String getUrl(String key);

    /**
     * Delete file from storage by key
     *
     * @param key path to the file in the storage
     */
    void delete(String key);

    /**
     * Check for existing file in storage by key
     *
     * @param key path to the file in the storage
     */
    boolean exist(String key);
}
