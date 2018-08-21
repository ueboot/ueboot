package com.ueboot.shiro.shiro;


import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * shiro 权限配置
 *
 * @author yangkui
 */
@Configuration
public class ShiroBaseConfigure {
    /**
     * 当shiroService对应的bean不存在存在时，会使用默认数据
     *
     * @param securityManager 权限框架
     * @return ShiroFilterFactoryBean
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Autowired SecurityManager securityManager,ShiroService shiroService) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl("#/login");
        Map<String, String> map = new HashMap<>();
        //默认所有请求做认证，对部分约定目录的请求不做拦截
        map.put("/","authc");
        map.put("/ueboot/shiro/public/","anon");
        map.put("/static/","anon");
        map.put("/public/","anon");
        Map<String, String> customMap = shiroService.addFilterChainDefinition();
        map.putAll(customMap);
        bean.setFilterChainDefinitionMap(map);
        Map<String, Filter> filterMap = new HashMap<>(1);
        //替换默认的用户认证实现
        filterMap.put("authc", new FormAuthenticationFilter() {
            //重载跳转登录页面的逻辑，默认父类是使用重定向，这里改为返回标示，供前端判断
            @Override
            protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.getWriter().write("{\"code\":401,\"errorMsg\":\"尚未登录，请登录!\"}");
            }
        });
        bean.setFilters(filterMap);
        return bean;
    }

    @Bean
    public Realm realm() {
        return new UserRealm();
    }


    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        //TODO 增加缓存
        return securityManager;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager securityManager
     * @return AuthorizationAttributeSourceAdvisor AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

}
