package com.ueboot.shiro.shiro.cache;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 基于Redis为shiro实现缓存功能，默认的shiro只提供了内存缓存和Ehcache实现
 * @author yangkui
 */
public class ShiroRedisCacheManger extends AbstractCacheManager {
    private String keyNamespace = null;

    private RedisTemplate<Object, Object> redisTemplate;

    public ShiroRedisCacheManger(String keyNamespace, RedisTemplate<Object, Object> redisTemplate) {
        this.keyNamespace = keyNamespace;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected Cache createCache(String s) throws CacheException {
        return new RedisCache(redisTemplate,keyNamespace);
    }
}
