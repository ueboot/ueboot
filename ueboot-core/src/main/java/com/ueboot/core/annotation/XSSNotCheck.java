package com.ueboot.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author yangkui
 * createTime:2019/4/99:50 PM
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface  XSSNotCheck {
}



