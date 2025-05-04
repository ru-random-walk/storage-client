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
package ru.random.walk.club_service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import ru.random.walk.client.StorageClient;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestContextConfiguration {
    @Bean
    @Primary
    public StorageClient storageClient() {
        return mock(StorageClient.class);
    }
}
```
- And Import test context configuration
```java
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestContextConfiguration.class)
public abstract class AbstractContainerTest {
//    ...
}
```