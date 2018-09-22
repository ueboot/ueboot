package com.ueboot.shiro.shiro;


import com.ueboot.core.condition.RedisDisabledCondition;
import com.ueboot.core.condition.RedisEnableCondition;
import com.ueboot.shiro.shiro.auditor.JpaAuditingAwareImpl;
import com.ueboot.shiro.shiro.cache.ShiroRedisCahceManger;
import com.ueboot.shiro.shiro.credential.RetryLimitHashedCredentialsMatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.redis.core.RedisTemplate;

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
@Slf4j
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
            //为了输出日志，重载实现类
            @Override
            public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
                log.info(request.getRemoteHost());
                return super.onPreHandle(request, response, mappedValue);
            }

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
    public Realm realm(CredentialsMatcher credentialsMatcher,UserRealm userRealm) {
        //自定义密码校验器
        userRealm.setCredentialsMatcher(credentialsMatcher);
        return userRealm;
    }

    /**
     * 当用户的环境配置了redisTemplate时则使用Redis做缓存
     * @param realm realm
     * @param redisTemplate spring RedisTemplate
     * @return DefaultWebSecurityManager
     */
    @Bean
    @Conditional(RedisEnableCondition.class)
    public DefaultWebSecurityManager defaultWebSecurityManager(Realm realm, RedisTemplate<Object,Object> redisTemplate) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        //使用自定义的Redis缓存实现，依赖redisTemplate，keyNamespace可以默认为空
        ShiroRedisCahceManger cacheManager = new ShiroRedisCahceManger("ueboot-shiro",redisTemplate);
        securityManager.setCacheManager(cacheManager);
        return securityManager;
    }
    /**
     * 当用户的环境没有配置redisTemplate时则使用ehcache做缓存
     * @param realm realm
     * @return DefaultWebSecurityManager
     */
    @Bean
    @Conditional(RedisDisabledCondition.class)
    public DefaultWebSecurityManager webSecurityManager(Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        //使用ehcache当缓存
        EhCacheManager cacheManager = new EhCacheManager();
        securityManager.setCacheManager(cacheManager);
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

    /***
     * 密码凭证匹配器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public CredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher  matcher=new RetryLimitHashedCredentialsMatcher();
        matcher.setHashAlgorithmName ("SHA-512");
        //散列的次数，比如散列两次
        matcher.setHashIterations (2);
        matcher.setStoredCredentialsHexEncoded (Boolean.TRUE);
        return matcher;
    }

    /**
     * 增加审计记录，根据登录用户补充创建人、修改人
     */
    @Bean
    public AuditorAware auditorAware(){
        return new JpaAuditingAwareImpl();
    }


}
