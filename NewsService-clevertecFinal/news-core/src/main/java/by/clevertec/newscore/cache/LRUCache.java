package by.clevertec.newscore.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K,V> implements Cache<K,V> {

    private final Map<K,V> cache;

    public LRUCache(int capacity) {
        this.cache = new LinkedHashMap<K,V>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
                return size() > capacity;
            }
        };
    }

    @Override
    public void put(K k, V v) {
        cache.put(k,v);
    }

    @Override
    public V get(K k) {
        return cache.get(k);
    }

    @Override
    public void delete(K k) {
        cache.remove(k);
    }
}
