package com.ueboot.core.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

/**
 * 当没有配置的缓存不是redis时
 * @author yangkui
 */
@Component
public class RedisDisabledCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment env = context.getEnvironment();
        String storeType = env.getProperty("spring.session.store-type");
        return !"redis".equalsIgnoreCase(storeType);
    }
}
