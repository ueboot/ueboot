package com.ueboot.core.shiro;


import java.util.List;
import java.util.Set;

/**
 * 必须有类实现该接口
 */
public interface ShiroService {




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
