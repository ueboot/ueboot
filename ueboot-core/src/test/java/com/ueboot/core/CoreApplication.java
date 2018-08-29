/*
 * Copyright (c)  2018, XiQiao
 * All rights reserved. 
 *
 * Id:CoreApplication.java   2018-08-29 20:00 wanglijun
 */
package com.ueboot.core;

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
 * @create: 2018-08-29 20:00
 * @version：1.0
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(basePackages = {"com.ueboot.core.repository"})
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(value = {"com.ueboot.core"})
public class CoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }
}

