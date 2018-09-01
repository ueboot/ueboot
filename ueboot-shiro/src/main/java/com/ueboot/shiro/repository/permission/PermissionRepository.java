/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-22 10:24:03
*/
package com.ueboot.shiro.repository.permission;

import com.ueboot.shiro.entity.Permission;
import com.ueboot.core.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
* 这个类里面使用spring data jpa 方式实现数据库的CRUD
* Created on 2018-08-22 10:24:03
* @author yangkui
* @since 2.1.0 by ueboot-generator
*/
@Repository
public interface PermissionRepository extends BaseRepository<Permission, Long>,PermissionBaseRepository {
    /**
     * 根据角色名称查询角色所属的权限
     * @param roleNames 角色名称集合
     * @return 权限列表
     */
    List<Permission> findByRoleNameIn(Set<String> roleNames);

    /**
     * 根据角色获取权限列表
     * @param roleId 角色ID
     * @return 资源列表
     */
    List<Permission> findByRoleId(Long roleId);
}
