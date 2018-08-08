/*
 * Copyright (c)  2018, XiQiao
 * All rights reserved. 
 *
 * Id:SpringContextUtils.java   2018-07-11 12:21 wanglijun
 */
package com.ueboot.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2018
 * </p>
 * <p>
 * Company: XiQiao
 * </p>
 *
 * @author: wanglijun
 * @create: 2018-07-11 12:21
 * @version：1.0
 */
@Component
@Slf4j
public class SpringContextUtils implements ApplicationContextAware {
    /**
     * Spring Context
     */
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextUtils.applicationContext == null) {
            SpringContextUtils.applicationContext = applicationContext;
        }
        log.info ("{}",SpringContextUtils.applicationContext);
    }

    /***
     * 根据一个bean的id获取配置文件中相应的bean
     * @param  beanId
     * @return Object
     */
    public static Object getBean(String beanId) throws BeansException {
        if (applicationContext.containsBean(beanId)) {
            applicationContext.getBean(beanId);
        }
        return null;
    }

    /***
     * 根据一个bean的类型获取配置文件中相应的bean
     *
     */
    public static <T> T getBeanByClass(Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(requiredType);
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     * @param name
     */
    public static Boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    /**
     * 获取Spring 上下文
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return  SpringContextUtils.applicationContext;
    }

}
