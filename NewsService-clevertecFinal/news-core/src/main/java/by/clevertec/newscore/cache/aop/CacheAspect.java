package by.clevertec.newscore.cache.aop;

import by.clevertec.newscore.cache.Cache;
import by.clevertec.newscore.cache.annotation.CacheDelete;
import by.clevertec.newscore.cache.annotation.CachePut;
import by.clevertec.newscore.cache.annotation.CacheUpdate;
import by.clevertec.newscore.cache.annotation.Cacheable;
import by.clevertec.newscore.domain.news.News;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Aspect
@Component
public class CacheAspect {

    private final CacheManager cacheManager;

    public CacheAspect(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Around("@annotation(cacheable)")
    public Object cacheGetMethod(ProceedingJoinPoint joinPoint, Cacheable cacheable) throws Throwable {

        String cacheName = cacheable.cacheName();
        Cache<Long, Object> cache = cacheManager.getCache(cacheName);
        Long key = (Long) joinPoint.getArgs()[0];
        Object cachedValue = cache.get(key);
        if (cachedValue != null) {
            return cachedValue;
        }

        News resultOfMethod = (News) joinPoint.proceed();
        cache.put(key, resultOfMethod);
        return resultOfMethod;
    }

    @Around("@annotation(cacheUpdate)")
    public Object cacheUpdateMethod(ProceedingJoinPoint joinPoint, CacheUpdate cacheUpdate) throws Throwable {

        Object result = joinPoint.proceed();
        if(result != null) {
            String cacheName = cacheUpdate.cacheName();
            Cache<Long, Object> cache = cacheManager.getCache(cacheName);
            Long keyFromEntity = getKeyFromEntity(result, cacheUpdate.key());
            cache.delete(keyFromEntity);
            cache.put(keyFromEntity, result);
        }
        return result;
    }

    @Around("@annotation(cachePut)")
    public Object cachePutMethod(ProceedingJoinPoint joinPoint, CachePut cachePut) throws Throwable {

        Object result = joinPoint.proceed();
        String cacheName = cachePut.cacheName();
        Cache<Long, Object> cache = cacheManager.getCache(cacheName);
        if(result != null) {
            Long keyFromEntity = getKeyFromEntity(result, cachePut.key());
            cache.put(keyFromEntity, result);
        }
        return result;
    }

    @Around("@annotation(cacheDelete)")
    public void cacheDelete(ProceedingJoinPoint joinPoint, CacheDelete cacheDelete) throws Throwable {
        Long key = (Long) joinPoint.getArgs()[0];
        joinPoint.proceed();

        String cacheName = cacheDelete.cacheName();
        Cache<Long, Object> cache = cacheManager.getCache(cacheName);
        cache.delete(key);
    }

    private Long getKeyFromEntity(Object entity, String key) {
        try {
            Field declaredField = entity.getClass().getDeclaredField(key);
            declaredField.setAccessible(true);
            return (Long) declaredField.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("No field : " + key +" in entity " + entity.getClass().getName(), e);
        }
    }
}
