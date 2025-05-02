# Quick start guide
- Import storage auto configuration `StorageAutoConfiguration`
```java
package ru.random.walk.club_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.random.walk.config.StorageAutoConfiguration;

@SpringBootApplication
@Import(StorageAutoConfiguration.class)
public class ClubServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClubServiceApplication.class, args);
    }

}
```
- Properties needed to set:
```yaml
storage:
  endpoint: ${STORAGE_ENDPOINT}
  bucketName: ${STORAGE_BUCKET_NAME}
  accessKey: ${STORAGE_ACCESS_KEY}
  secretKey: ${STORAGE_SECRET_KEY}
  region: ${STORAGE_REGION}
  temporaryUrlTtlInMinutes: 5
  servicePath: club-service
```
- Mock `StorageClient` client in base test class or for all tests that up whole spring context
```java
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractContainerTest {
    private static final PostgreSQLContainer<?> DATABASE_CONTAINER;
    private static final KafkaContainer KAFKA_CONTAINER;

    @MockitoBean
    private StorageClient storageClient;
//    ...
}
```