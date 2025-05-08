package ru.random.walk.client;

import ru.random.walk.model.FileType;

import java.io.File;
import java.io.InputStream;

public interface StorageClient {
    /**
     * Upload input stream file with explicit key
     *
     * @param explicitKey path to the file in the storage
     * @return temporary url for input file
     */
    String uploadAndGetUrl(File input, String explicitKey);

    /**
     * Upload input stream file with explicit key
     *
     * @param explicitKey path to the file in the storage
     * @return temporary url for input file
     */
    String uploadAndGetUrl(InputStream input, String explicitKey);

    /**
     * Upload input stream file with type and explicit key
     *
     * @param explicitKey path to the file in the storage
     * @return temporary url for input file
     */
    String uploadAndGetUrl(InputStream input, String explicitKey, FileType fileType);

    /**
     * Get temporary url for uploaded file by raw explicit key
     *
     * @param explicitKey path to the file in the storage
     * @return temporary url for input file
     */
    String getUrl(String explicitKey);

    /**
     * Delete file from storage by explicitKey
     *
     * @param explicitKey path to the file in the storage
     */
    void delete(String explicitKey);

    /**
     * Check for existing file in storage by explicitKey
     *
     * @param explicitKey path to the file in the storage
     */
    boolean exist(String explicitKey);
}
