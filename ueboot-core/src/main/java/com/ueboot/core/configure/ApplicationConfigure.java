/*
 * Copyright (c)  2017
 * All rights reserved.
 * ApplicationConfigure.java 2017-09-24 下午4:09
 */

package com.ueboot.core.configure;

import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 描述:应用基础配置
 * 支持模型类的审计注解
 *
 * @author yangkui create on 2017-09-24 下午4:09.
 * @since 1.0
 */
@Configuration
@EnableJpaAuditing
public class ApplicationConfigure implements AuditorAware<String> {

    @Bean
    public Log4jFastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        return new Log4jFastJsonHttpMessageConverter();
    }

    /**
     * Returns the current auditor of the application.
     *
     * @return the current auditor
     */
    @Override
    public String getCurrentAuditor() {
        //使用当前登录用户名称作为创建人和最后修改人字段的值
        if( SecurityUtils.getSubject()!=null){
            return (String) SecurityUtils.getSubject().getPrincipal();
        }
        return null;
    }
}