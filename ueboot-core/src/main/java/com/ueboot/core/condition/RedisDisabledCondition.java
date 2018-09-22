package com.ueboot.core.condition;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 当没有配置的缓存不是redis时
 * @author yangkui
 */
public class RedisDisabledCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        RelaxedPropertyResolver resolver = new RelaxedPropertyResolver(
                context.getEnvironment(), "spring.cache.");
        return !"redis".equals(resolver.getProperty("type"));
    }
}
