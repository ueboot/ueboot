package com.ueboot.core.http.annotation;

import java.lang.annotation.*;

/**
 * 用于修改spring 默认的pageable对象最大值只有1000的问题，
 * 通过增加注解的方式明确指明某个查询接口允许超过最大限制。不能默认设置为最大值，防止页面传入的参数过大导致DB无法返回
 * @author yangkui
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface PageableLimits {
    int maxSize() default Integer.MAX_VALUE;

    int minSize() default 0;
}