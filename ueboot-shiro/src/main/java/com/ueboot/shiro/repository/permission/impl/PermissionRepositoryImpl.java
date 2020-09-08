/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-22 10:24:03
*/
package com.ueboot.shiro.repository.permission.impl;

import com.ueboot.core.jpa.repository.query.StringQuery;
import com.ueboot.shiro.entity.Permission;
import com.ueboot.shiro.repository.permission.bo.PermissionBo;
import org.springframework.stereotype.Repository;
import com.ueboot.core.jpa.repository.DefaultJpaRepository;
import com.ueboot.shiro.repository.permission.PermissionBaseRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

/**
* 自定义接口实现类，可以使用父类DefaultJpaRepository当中的find(),findBySql等方法实现自定义的StringQuery查询
* 相关使用示例，参见文档http://docs.ueboot.com
* Created on 2018-08-22 10:24:03
* @author yangkui
* @since 2.1.0 by ueboot-generator
*/
@Slf4j
@Repository
public class PermissionRepositoryImpl extends DefaultJpaRepository<Permission,Long> implements PermissionBaseRepository {

    /**
     * 根据角色名称查询角色所属的权限集合，只返回需要的字段，避免性能问题
     *
     * @param roleNames 角色名称集合
     * @return 权限列表
     */
    @Override
    public List<PermissionBo> findPermissionsByRoleNameIn(Set<String> roleNames) {
        StringQuery query = StringQuery.newQuery()
                .query ("select r.id as resourceId, r.name as resourceName, r.resourceType as resourceType" +
                        ", r.url as url, r.themeJson as themeJson,r.permission as permission" +
                        ", r.parentName as parentName,r.rank as rank,r.parent.id as parentId from Resources r,Permission p where p.resource.id = r.id")
                .predicateNotEmpty(roleNames)
                .query(" and p.role.name in (:roleNames)")
                .inParam("roleNames", roleNames)
                .predicate(Boolean.TRUE).build();
        return find(query, PermissionBo.class);
    }
}
