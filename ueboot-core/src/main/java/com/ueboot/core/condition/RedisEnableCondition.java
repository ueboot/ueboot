package com.ueboot.core.condition;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 判断是否开启redis缓存相关条件
 * 当存在spring.cache.type的值为redis时才开启
 * @author yangkui
 */
public class RedisEnableCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        RelaxedPropertyResolver resolver = new RelaxedPropertyResolver(
                context.getEnvironment(), "spring.cache.");
        return "redis".equals(resolver.getProperty("type"));
    }
}
