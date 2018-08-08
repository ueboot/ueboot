package com.ueboot.shiro.shiro;


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
     * 判断用户是否存在
     *
     * @param username 用户名
     * @param password 未加密的密码
     * @return 是否存在
     */
    boolean userExist(String username, String password);


    /**
     * 判断用户密码是否过期
     *
     * @param username 用户名
     * @param password 密码
     * @return true 过期,false 未过期
     */
    boolean isPassed(String username, String password);

    /**
     * 获取用户的角色名称集合
     *
     * @param username 用户名
     * @return 用户角色列表
     */
    Set<String> getUserRoleNames(String username);

    /**
     * 获取用户的权限列表
     *
     * @param username 用户名
     * @return 用户权限列表
     */
    Set<String> getUserPermission(String username);


    /**
     * 获取用户的权限列表
     *
     * @param role 角色名称
     * @return 用户权限列表
     */
    List<String> getRolePermission(String role);

}
