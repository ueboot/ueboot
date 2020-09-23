/*
 * Copyright (c)  2017
 * All rights reserved.
 * ApplicationConfigure.java 2017-09-24 下午4:09
 */

package com.ueboot.core.configure;

import com.ueboot.core.service.HttpRequestValidatorService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

/**
 * 描述:应用基础配置
 * 支持模型类的审计注解
 *
 * @author yangkui create on 2017-09-24 下午4:09.
 * @since 1.0
 */
@Configuration
@EnableJpaAuditing
public class ApplicationConfigure{

    @Bean
    @ConditionalOnMissingBean(name = "fastJsonHttpMessageConverter")
    public Log4jFastJsonHttpMessageConverter fastJsonHttpMessageConverter(HttpRequestValidatorService httpRequestValidatorService, SpringValidatorAdapter springValidatorAdapter) {
        return new Log4jFastJsonHttpMessageConverter(httpRequestValidatorService,springValidatorAdapter);
    }
}