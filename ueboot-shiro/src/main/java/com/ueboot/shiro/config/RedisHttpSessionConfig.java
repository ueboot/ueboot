package com.ueboot.shiro.config;

import com.ueboot.core.condition.RedisEnableCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author yangkui
 * 设置只有开启redis配置后，才使用当前配置，
 * 防止系统未使用redis时，代码因为加载了当前框架，导致会自动连接redis而报错
 */
@Conditional(RedisEnableCondition.class)
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 6000, redisNamespace = "ueboot_shiro")
public class RedisHttpSessionConfig {

}
