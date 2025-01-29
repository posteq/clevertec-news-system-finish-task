package by.clevertec.newscore.cache;

public interface Cache <K,V>{
    void put(K k ,V v);
    V get(K k);
    void delete(K k);
}
