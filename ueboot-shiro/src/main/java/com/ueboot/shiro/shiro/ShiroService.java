package com.ueboot.shiro.shiro;


import com.ueboot.shiro.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Shiro权限认证服务类，代码集成时只需要实现该接口当中的所有方法即可
 * <p>
 * 必须有类实现该接口。且bean的名称为“shiroService”
 *
 * @author yangkui
 */
public interface ShiroService {


    /**
     * 对shiro的FilterChainDefinitionMap 添加自定义的配置
     * 如：
     *
     {@code
      Map<String, String> map = new HashMap<>(10);
      //配置指定路径是否需要登录、或不需要登录,示例
     
      //所有/public开头的路径都不需要登录即可访问
      map.put("/public/", "anon");
      //所有路径需要授权才可以访问，和上面的配置作为互补。
      map.put("/", "authc");

     }
     * @return 定义好的FilterChainDefinition Map
     */
    Map<String, String> addFilterChainDefinition();


    /**
     * 根据用户名用户信息
     *
     * @param username 用户名
     * @return 是否存在
     */
    User getUser(String username);


    /**
     * 获取用户的角色名称集合(要求角色名称唯一）
     *
     * @param userName 用户名
     * @return 用户角色列表
     */
    Set<String> getUserRoleNames(String userName);

    /**
     * 获取用户的权限列表
     *
     * @param roleCodes 角色代码列表
     * @return 用户权限列表
     */
    Set<String> getRolePermission(Set<String> roleCodes);

    /**
     * 获取密码过期月份数。新用户和修改密码后，会自动将当前过期时间设置为当前时间往后几个月
     * @return 过期月份数
     */
    int getPasswordExpiredMonth();

}
