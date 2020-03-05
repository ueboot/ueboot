package com.ueboot.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 加上此字段后，不再输出当前请求或者返回类的日志
 * @author yangkui
 * createTime:2019/4/99:50 PM
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface NotLog {
}



