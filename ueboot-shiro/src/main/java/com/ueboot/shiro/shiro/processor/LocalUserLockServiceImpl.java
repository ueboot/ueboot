package com.ueboot.shiro.shiro.processor;

import com.ueboot.core.condition.RedisDisabledCondition;
import com.ueboot.shiro.entity.UserInfo;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangkui
 * createTime:2020/2/2721:36
 */
@Conditional(RedisDisabledCondition.class)
@Component
public class LocalUserLockServiceImpl implements UserLockService {
    private Map<String, UserInfo> cache = new HashMap<>();

    @Override
    public void lockUser(String username) {
        cache.put(LOCK_KEY + username, new UserInfo(username, new Date()));
    }

    @Override
    public UserInfo getLockUserInfo(String username) {
        return cache.get(LOCK_KEY + username);
    }
}
