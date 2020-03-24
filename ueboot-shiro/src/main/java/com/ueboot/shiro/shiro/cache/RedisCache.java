package com.ueboot.shiro.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;

/**
 * 使用Redis实现shiro缓存接口。所有shiro相关的缓存统一存放到指定的一个namespace目录下
 * @author yangkui
 */
public class RedisCache implements Cache {

    public static String keyNamespace = "ueboot_shiro";

    private RedisTemplate<Object,Object> redisTemplate;

    public RedisCache(RedisTemplate<Object, Object> redisTemplate,String namespace) {
        this.redisTemplate = redisTemplate;
        if(StringUtils.hasText(namespace)){
            keyNamespace = namespace;
        }
    }

    private String getKey(Object key){
        return  keyNamespace+":"+key;
    }

    @Override
    public Object get(Object o) throws CacheException {
        return this.redisTemplate.opsForValue().get(this.getKey(o));
    }

    @Override
    public Object put(Object o, Object o2) throws CacheException {
         this.redisTemplate.opsForValue().set(this.getKey(o), o2);
         return o2;
    }

    @Override
    public Object remove(Object o) throws CacheException {
         this.redisTemplate.delete(this.getKey(o));
         return o;
    }

    @Override
    public void clear() throws CacheException {
          Set<Object>  keys =  this.redisTemplate.keys(this.getKey("*"));
          this.redisTemplate.delete(keys);
    }

    @Override
    public int size() {
        return this.redisTemplate.keys(this.getKey("*")).size();
    }

    @Override
    public Set keys() {
        return this.redisTemplate.keys(this.getKey("*"));
    }

    @Override
    public Collection values() {
       return this.redisTemplate.opsForValue().multiGet(this.keys());
    }
}
