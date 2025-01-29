package by.clevertec.newscore.cache.aop;

import by.clevertec.newscore.cache.LFUCache;
import by.clevertec.newscore.cache.LRUCache;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cache")
@Getter
@Setter
public class CacheConfig {

    private String algorithm;
    private int capacity;

    @Bean
    public CacheManager cacheManager() {
        CacheManager manager = new CacheManager();
        if ("LRU".equalsIgnoreCase(algorithm)) {
            manager.addCache("default", new LRUCache<>(capacity));
        } else if ("LFU".equalsIgnoreCase(algorithm)) {
            manager.addCache("default", new LFUCache<>(capacity));
        }
        return manager;
    }
}
