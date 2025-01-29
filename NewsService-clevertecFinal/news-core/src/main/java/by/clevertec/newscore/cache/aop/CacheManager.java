package by.clevertec.newscore.cache.aop;

import by.clevertec.newscore.cache.Cache;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {

    private final Map<String, Cache<Long, Object>> caches = new HashMap<>();

    public void addCache(String name, Cache<Long, Object> cache) {
        caches.put(name, cache);
    }

    public Cache<Long, Object> getCache(String name) {
        return caches.get(name);
    }
}
