package ru.random.walk;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.random.walk.client.StorageClient;
import ru.random.walk.config.StorageConfiguration;

@Configuration
public class MockedStorageContext {
    @MockitoBean
    private StorageClient storageClient;
    @MockitoBean
    private StorageConfiguration storageConfiguration;
}
