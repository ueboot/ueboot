/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-22 10:24:03
*/
package com.ueboot.shiro.service.permission;

import com.ueboot.shiro.entity.Permission;
import com.ueboot.core.service.BaseService;

import java.util.List;

/**
 * Created on 2018-08-22 10:24:03
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
public interface PermissionService extends BaseService<Permission> {

    /**
     * 根据角色获取权限列表
     * @param roleId 角色ID
     * @return 资源列表
     */
    List<Permission> findByRoleId(Long roleId);

    /**
     * 保存某个角色ID对应的所有权限
     * @param roleId 角色ID
     * @param resourceIds 资源ID列表
     */
    void saveRolePermission(Long roleId,Long[] resourceIds);

}
