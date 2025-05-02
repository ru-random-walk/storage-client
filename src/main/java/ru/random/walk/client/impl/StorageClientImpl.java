package ru.random.walk.client.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.random.walk.client.StorageClient;
import ru.random.walk.config.StorageProperties;

import java.io.File;

@Slf4j
@AllArgsConstructor
public class StorageClientImpl implements StorageClient {
    private final AmazonS3Client s3Client;
    private final StorageProperties properties;

    @Override
    public String uploadAndGetUrl(File file, String key) {
        return "";
    }

    @Override
    public File get(String key) {
        return null;
    }

//    @PostConstruct
//    private void send() {
//        File file = new File("my.png");
//        if (!FileUtil.isImage(file)) {
//            log.info("File is not image!");
//            return;
//        }
//        StringBuilder content = new StringBuilder();
//        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                content.append(line);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        log.info("File = [{}]", content);
//        s3Client.putObject(properties.bucketName(), "maks", file);
//        var expirationDate = Date.from(now().plusMinutes(1).atZone(ZoneOffset.systemDefault()).toInstant());
//        var url = s3Client.generatePresignedUrl(properties.bucketName(), "maks", expirationDate);
//        log.info("Url = {}", url);
//    }
}
