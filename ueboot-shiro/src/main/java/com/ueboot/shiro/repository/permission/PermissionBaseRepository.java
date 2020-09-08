/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-22 10:24:03
*/
package com.ueboot.shiro.repository.permission;

import com.ueboot.shiro.repository.permission.bo.PermissionBo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
* 1.这里可以写基于StringQuery方式的自定义的接口，但是需要在实现类PermissionRepositoryImpl当中进行实现，
* 2.如果使用Spring Data JPA语法，则写到PermissionRepository接口当中
* 3.Service类注入接口时直接注入 PermissionRepository接口即可，当前接口不需要注入
* Created on 2018-08-22 10:24:03
* @author yangkui
* @since 2.1.0 by ueboot-generator
*/
public interface PermissionBaseRepository  {
    /**
     * 根据角色名称查询角色所属的权限集合，只返回需要的字段，避免性能问题
     * @param roleNames 角色名称集合
     * @return 权限列表
     */
    List<PermissionBo> findPermissionsByRoleNameIn(Set<String> roleNames);
}