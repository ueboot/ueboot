package com.ueboot.shiro;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * 描述:本地测试使用的 应用启动类
 *
 * @author yangkui
 * @since 1.0
 */

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(value = {"com.ueboot.shiro","com.ueboot.core"})
public class ShiroLocalStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShiroLocalStartApplication.class, args);
    }
}
