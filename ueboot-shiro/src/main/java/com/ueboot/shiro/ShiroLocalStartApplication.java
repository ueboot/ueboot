package com.ueboot.shiro;


import net.hasor.spring.boot.EnableHasor;
import net.hasor.spring.boot.EnableHasorWeb;
import net.hasor.spring.boot.WorkAt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * 描述:本地测试使用的 应用启动类
 *
 * @author yangkui
 * @since 1.0
 */

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(value = {"com.ueboot.core","com.ueboot.shiro"})
@EnableSwagger2
@EnableHasorWeb(at = WorkAt.Interceptor)
@EnableHasor(useProperties = true)
public class ShiroLocalStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShiroLocalStartApplication.class, args);
    }
}
