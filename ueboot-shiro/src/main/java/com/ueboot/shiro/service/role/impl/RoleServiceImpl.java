/*
 * Copyright (c)  2018
 * All rights reserved.
 * 2018-08-21 09:40:34
 */
package com.ueboot.shiro.service.role.impl;

import com.ueboot.shiro.entity.Permission;
import com.ueboot.shiro.entity.Role;
import com.ueboot.core.repository.BaseRepository;
import com.ueboot.shiro.repository.permission.PermissionRepository;
import com.ueboot.shiro.repository.role.RoleRepository;
import com.ueboot.core.service.impl.BaseServiceImpl;
import com.ueboot.shiro.repository.userrole.UserRoleRepository;
import com.ueboot.shiro.service.role.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created on 2018-08-21 09:40:34
 *
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Slf4j
@Service
@ConditionalOnMissingBean(name = "roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Resource
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    protected BaseRepository getBaseRepository() {
        return roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 60, propagation = Propagation.REQUIRED)
    public void deleteRole(Long[] roleIds) {
        //先删除角色关联的资源
        for (int i = 0; i < roleIds.length; i++) {
            Long roleId = roleIds[i];
            List<Permission> permissions = permissionRepository.findByRoleId(roleId);
            permissionRepository.deleteAll(permissions);
            this.delete(roleId);
        }
    }

    @Override
    public Page<Role> findByName(Pageable pageable, String name) {
        return this.roleRepository.findByNameLike(pageable, name);
    }


    @Override
    public Long statisticUserByRoleId(Long id) {
        return userRoleRepository.statisticUserSumByRoleId(id);
    }
}
