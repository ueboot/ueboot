/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-21 09:39:51
*/
package com.ueboot.shiro.service.userrole;

import com.ueboot.shiro.entity.UserRole;
import com.ueboot.core.service.BaseService;

import java.util.List;

/**
 * Created on 2018-08-21 09:39:51
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
public interface UserRoleService extends BaseService<UserRole> {


    void saveUserRole(Long userId,Long[] roleIds);

    /**
     * 根据用户ID查询用户所属角色
     * @param userId 用户ID
     * @return 角色列表
     */
    List<UserRole> findByUserId(Long userId);

    /**
     * 根据角色ID查询角色所属用户列表
     * @param roleId 角色ID
     * @return 用户列表
     */
    List<UserRole> findByRoleId(Long roleId);
}
