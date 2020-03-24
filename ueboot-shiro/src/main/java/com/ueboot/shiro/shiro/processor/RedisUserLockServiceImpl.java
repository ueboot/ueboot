package com.ueboot.shiro.shiro.processor;

import com.ueboot.core.condition.RedisEnableCondition;
import com.ueboot.shiro.entity.UserInfo;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author yangkui
 * createTime:2020/2/2721:39
 */
@Conditional(RedisEnableCondition.class)
@Component
public class RedisUserLockServiceImpl implements UserLockService {


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void lockUser(String username) {
        redisTemplate.opsForValue().set(LOCK_KEY + username, new UserInfo(username, new Date()));

    }

    @Override
    public UserInfo getLockUserInfo(String username) {
        Object o = redisTemplate.opsForValue().get(LOCK_KEY + username);
        if(o==null){
            return null;
        }
        return (UserInfo)o;
    }
}
