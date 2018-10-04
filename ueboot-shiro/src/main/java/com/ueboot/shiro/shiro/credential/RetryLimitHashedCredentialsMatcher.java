/*
 * Copyright (c)  2018, XiQiao
 * All rights reserved.
 *
 * Id:RetryLimitHashedCredentialsMatcher.java   2018-07-09 22:25 wanglijun
 */
package com.ueboot.shiro.shiro.credential;

import com.ueboot.shiro.shiro.cache.RedisCache;
import com.ueboot.shiro.shiro.cache.ShiroRedisCacheManger;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2018
 * </p>
 * <p>
 * Company: XiQiao
 * </p>
 *
 * @author: wanglijun
 * @version：1.0
 */
@Slf4j
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    /***
     * 密码重试的KEY
     */
    public static final String PASSWORD_RETRY_CACHE="SHIRO2:PASSWORD:RETRY:COUNT:{0}";

    /***
     * Redis Tamplete
     */
    private RedisTemplate redisTemplate;

    /**密码重试最大次数* 默认为：5*/
    private Integer retryMaxCount= 5;

    /***
     *
     * @param redisTemplate
     */
    public RetryLimitHashedCredentialsMatcher(RedisTemplate redisTemplate){
        this.redisTemplate=redisTemplate;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }



    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String userName = (String)token.getPrincipal();

        String key=this.getKey(userName);


        AtomicInteger retryCount;


        if(!redisTemplate.hasKey(key)) {
            retryCount =new AtomicInteger(0);
        }else{
            retryCount = (AtomicInteger) redisTemplate.opsForValue().get(key);
        }

        log.info("userName:{},retryCount:{}",userName,retryCount);
        //retry count + 1
        //大于等于最大次数
        if(retryCount.incrementAndGet() >= this.retryMaxCount) {
            throw new ExcessiveAttemptsException("超过最大重试次数，最大值："+this.retryMaxCount);
        }

        boolean matches = super.doCredentialsMatch(token, info);

        if(matches) {
            redisTemplate.delete(key);
        }else{
            //默认设置为1天
            redisTemplate.opsForValue().set(key,retryCount);
            redisTemplate.expire(key,1,TimeUnit.HOURS);
            log.info("userName:{},retryCount:{}",userName,retryCount);
        }
        return matches;
    }

    /**
     * @return the retryMaxCount 密码最大重试次数
     */
    public Integer getRetryMaxCount() {
        return retryMaxCount;
    }

    /**
     * @param retryMaxCount the 密码最大重试次数 to set
     */
    public void setRetryMaxCount(Integer retryMaxCount) {
        this.retryMaxCount = retryMaxCount;
    }


    private String getKey(String userName){
        return MessageFormat.format(PASSWORD_RETRY_CACHE,userName);
    }

}
