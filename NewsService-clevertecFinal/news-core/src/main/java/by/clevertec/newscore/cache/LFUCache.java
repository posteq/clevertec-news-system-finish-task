package by.clevertec.newscore.cache;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;

public class LFUCache<K,V> implements Cache<K,V> {

    private final int capacity;
    private final Map<K,Node> cache;
    private final TreeMap<Integer, LinkedHashSet<Node>> frequencyMap;
    private int minFrequency;

    class Node {
        K key;
        V value;
        int frequency;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            frequency = 1;
        }
    }

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.frequencyMap = new TreeMap<>();
        this.minFrequency = 0;
    }

    @Override
    public void put(K k, V v) {
        if(capacity >= 0) {
            Node node = cache.get(k);
            if (node != null) {
                increaseFrequency(node);
                node.value = v;
                return ;
            }
            if (cache.size() >= capacity) {
                evict();
            }
            node = new Node(k, v);
            cache.put(k, node);
            frequencyMap.computeIfAbsent(1, i -> new LinkedHashSet<>()).add(node);
            minFrequency = 1;
        }
    }

    private void evict() {
        LinkedHashSet<Node> nodes = frequencyMap.get(minFrequency);
        Node next = nodes.iterator().next();
        nodes.remove(next);
        if(nodes.isEmpty()){
            frequencyMap.remove(minFrequency);
        }
        cache.remove(next.key);
        minFrequency = frequencyMap.firstKey();
    }

    @Override
    public V get(K k) {
        Node node = cache.get(k);
        if(node != null) {
            increaseFrequency(node);
            return node.value;
        }
        return null;
    }

    private void increaseFrequency(Node node) {
        int oldFrequency = node.frequency;
        frequencyMap.get(oldFrequency).remove(node);
        if(frequencyMap.get(oldFrequency).isEmpty()) {
            frequencyMap.remove(oldFrequency);
            if(oldFrequency == minFrequency) {
                minFrequency++;
            }
        }
        node.frequency++;
        frequencyMap.computeIfAbsent(node.frequency,
                                    k -> new LinkedHashSet<>())
                .add(node);
    }

    @Override
    public void delete(K k) {
        Node node = cache.get(k);
        if(node != null) {
            int frequency = node.frequency;
            frequencyMap.get(frequency).remove(node);
            if(frequencyMap.get(frequency).isEmpty()) {
                frequencyMap.remove(frequency);
                if(frequency == minFrequency) {
                    minFrequency++;
                }
            }
        }
    }
}
