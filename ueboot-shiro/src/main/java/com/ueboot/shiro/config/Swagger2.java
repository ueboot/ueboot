package com.ueboot.shiro.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * swagger2相关配置
 * 默认不放开，防止发布生产时自动开放了。需要集成方根据实际场景放开配置
 * @author yangkui
 */
@Configuration
@ConditionalOnProperty(value = "ueboot.shiro.swagger.enable",havingValue = "true")
public class Swagger2 {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ueboot.shiro.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ueboot-shiro接口文档")
                .description("ueboot-shiro所有功能都以接口形式提供访问，可以直接用ueboot-view的页面，也可以基于该接口自行开发界面")
                .termsOfServiceUrl("http://www.ueboot.com/")
                .version("3.0.0")
                .build();
    }
}
