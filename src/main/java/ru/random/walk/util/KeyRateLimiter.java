package ru.random.walk.util;

import com.google.common.util.concurrent.RateLimiter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class KeyRateLimiter<K extends Comparable<K>> {
    private final Map<K, RateLimiter> keyToRateLimiter = new ConcurrentHashMap<>();
    private final Duration period;

    public boolean limitExceeded(K key) {
        return !keyToRateLimiter.computeIfAbsent(key, this::createNewLimiter)
                .tryAcquire();
    }

    public void throwIfRateLimitExceeded(K key, Supplier<RuntimeException> exceptionSupplier) {
        if (limitExceeded(key)) {
            exceptionSupplier.get();
        }
    }

    private RateLimiter createNewLimiter(K k) {
        return RateLimiter.create((double) 1 / period.getSeconds());
    }
}
