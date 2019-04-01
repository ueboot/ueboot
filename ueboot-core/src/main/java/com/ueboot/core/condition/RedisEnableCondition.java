package com.ueboot.core.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 判断是否开启redis缓存相关条件
 * 当存在spring.cache.type的值为redis时才开启
 * @author yangkui
 */
public class RedisEnableCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment env = context.getEnvironment();
        String storeType = env.getProperty("spring.session.store-type");
        return "redis".equalsIgnoreCase(storeType);
    }
}
