package com.ueboot.core.configure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ueboot.core.condition.RedisEnableCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Pool;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;

/**
 * @author yangkui
 * Redis相关配置，默认配置值，只有当配置了spring.cache.type=redis 时才有效
 */
@Slf4j
@Configuration
@EnableCaching
@Conditional(RedisEnableCondition.class)
public class RedisConfig {
    /**
     * 一定要定义该bean，防止启动时出现以下异常
     * Caused by: redis.clients.jedis.exceptions.JedisDataException: ERR unknown command: CONFIG
     * @return Redis配置
     */
    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    @Bean
    public KeyGenerator wiselyKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };

    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(
            JedisConnectionFactory factory, RedisProperties redisProperties) {
        RedisTemplate template = new StringRedisTemplate(factory);
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        //Value
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        //KEY
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        JedisPoolConfig config = new JedisPoolConfig();
        //重新设置pool相关配置
        RedisProperties.Pool pool = redisProperties.getPool();
        config.setTestOnBorrow(true);
        config.setMaxIdle(pool.getMaxIdle());
        config.setMaxTotal(pool.getMaxActive());
        config.setMaxWaitMillis(pool.getMaxWait());
        factory.setPoolConfig(config);
        template.setConnectionFactory(factory);
        template.afterPropertiesSet();
        return template;
    }



} 