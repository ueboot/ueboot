/*
 * Copyright (c)  2018, XiQiao
 * All rights reserved. 
 *
 * Id:RetryLimitHashedCredentialsMatcher.java   2018-07-09 22:25 wanglijun
 */
package com.ueboot.shiro.shiro.credential;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
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
 * @create: 2018-07-09 22:25
 * @version：1.0
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
    /**密码重试缓存*/
    private Map<String, AtomicInteger> passwordRetryCache=new HashMap<> ();
    /**密码重试最大次数* 默认为：5*/
    private Integer retryMaxCount= 5;
    public RetryLimitHashedCredentialsMatcher() {
        if(CollectionUtils.isEmpty (passwordRetryCache)){
            //TODO 改为按天计算或者存放到Redis当中
            passwordRetryCache=new HashMap<> ();
        }
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String userName = (String)token.getPrincipal();
        //retry count + 1
        AtomicInteger retryCount = passwordRetryCache.get(userName);
        if(retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(userName, retryCount);
        }

        if(retryCount.incrementAndGet() > this.retryMaxCount) {
            throw new ExcessiveAttemptsException("超过最大重试次数，最大值："+this.retryMaxCount);
        }

        boolean matches = super.doCredentialsMatch(token, info);

        if(matches) {
            passwordRetryCache.remove(userName);
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

}
