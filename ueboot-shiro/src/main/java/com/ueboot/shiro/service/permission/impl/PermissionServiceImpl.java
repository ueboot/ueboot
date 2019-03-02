/*
 * Copyright (c)  2018
 * All rights reserved.
 * 2018-08-22 10:24:03
 */
package com.ueboot.shiro.service.permission.impl;

import com.ueboot.core.repository.BaseRepository;
import com.ueboot.core.service.impl.BaseServiceImpl;
import com.ueboot.shiro.entity.Permission;
import com.ueboot.shiro.entity.Resources;
import com.ueboot.shiro.entity.Role;
import com.ueboot.shiro.repository.permission.PermissionRepository;
import com.ueboot.shiro.repository.role.RoleRepository;
import com.ueboot.shiro.service.permission.PermissionService;
import com.ueboot.shiro.shiro.ShiroEventListener;
import com.ueboot.shiro.shiro.UserRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2018-08-22 10:24:03
 *
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Slf4j
@Service
@ConditionalOnMissingBean(name = "permissionService")
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    @Resource
    private PermissionRepository permissionRepository;

    @Resource
    private RoleRepository roleRepository;

    // shirou 权限日志记录
    @Resource
    private ShiroEventListener shiroEventListener;


    @Resource
    private UserRealm userRealm;

    @Override
    protected BaseRepository getBaseRepository() {
        return permissionRepository;
    }

    /**
     * 根据角色获取权限列表
     *
     * @param roleId 角色ID
     * @return 资源列表
     */
    @Override
    public List<Permission> findByRoleId(Long roleId) {
        return permissionRepository.findByRoleId(roleId);
    }

    /**
     * 保存某个角色ID对应的所有权限
     *
     * @param roleId      角色ID
     * @param resourceIds 资源ID列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void saveRolePermission(Long roleId, Long[] resourceIds) {
        //先删除同一个角色之前的权限，再插入新的权限
        List<Permission> old = this.findByRoleId(roleId);

        Long[] oldResourceIds = new Long[old.size()];   // 记录原始资源IDs
        if (!old.isEmpty()) {
            permissionRepository.deleteAll(old);
            // 将资源列表转化为资源ID数组
            oldResourceIds = old.stream().map(Permission::getId).collect(Collectors.toList()).toArray(new Long[old.size()]);
        }

        Role role = roleRepository.getOne(roleId);
        Assert.notNull(role, "roleId对应的角色不存在,roleId:" + roleId);
        Arrays.asList(resourceIds).forEach((rid) -> {
            Resources r = new Resources();
            r.setId(rid);
            Permission p = new Permission();
            p.setResource(r);
            p.setRole(role);
            permissionRepository.save(p);
        });
        //清除权限缓存
        if (userRealm.getAuthorizationCache() != null) {
            userRealm.getAuthorizationCache().clear();
        }

        // 记录权限日志
        String optUserName = (String) SecurityUtils.getSubject().getPrincipal();
        this.shiroEventListener.saveRolePermissionEvent(optUserName, roleId, oldResourceIds, resourceIds);
    }
}
