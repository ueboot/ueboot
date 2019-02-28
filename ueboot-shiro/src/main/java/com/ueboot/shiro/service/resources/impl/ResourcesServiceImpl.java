/*
 * Copyright (c)  2018
 * All rights reserved.
 * 2018-08-08 14:06:03
 */
package com.ueboot.shiro.service.resources.impl;

import com.ueboot.core.repository.BaseRepository;
import com.ueboot.core.service.impl.BaseServiceImpl;
import com.ueboot.shiro.entity.Permission;
import com.ueboot.shiro.entity.Resources;
import com.ueboot.shiro.entity.UserRole;
import com.ueboot.shiro.repository.permission.PermissionRepository;
import com.ueboot.shiro.repository.resources.ResourcesRepository;
import com.ueboot.shiro.repository.userrole.UserRoleRepository;
import com.ueboot.shiro.service.resources.ResourcesService;
import com.ueboot.shiro.shiro.ShiroEventListener;
import com.ueboot.shiro.shiro.ShiroService;
import com.ueboot.shiro.shiro.UserRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created on 2018-08-08 14:06:03
 *
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Slf4j
@Service
@ConditionalOnMissingBean(name = "resourcesService")
public class ResourcesServiceImpl extends BaseServiceImpl<Resources> implements ResourcesService {
    @Resource
    private ResourcesRepository resourcesRepository;

    @Resource
    private ShiroEventListener shiroEventListener;


    @Resource
    private PermissionRepository permissionRepository;

    @Resource
    private ShiroService shiroService;

    @Resource
    private UserRoleRepository userRoleRepository;


    @Override
    protected BaseRepository getBaseRepository() {
        return resourcesRepository;
    }


    /**
     * 根据资源类型查找资源列表
     *
     * @param resourcesType 资源类型
     * @return 资源列表
     */
    @Override
    public List<Resources> findByResourceType(String resourcesType) {
        return resourcesRepository.findByResourceType(resourcesType);
    }

    /**
     * 根据用户名查找用户的授权的资源
     *
     * @param username 用户名
     * @return 当前用户授权后的资源列表
     */
    @Override
    public Collection<Resources> getUserResources(String username) {
        //root用户返回所有菜单，防止root账户还需要授权才能访问
        if (UserRealm.SUPER_USER.equals(username)) {
            return this.resourcesRepository.findAll();
        }

        //找出用户的角色，根据角色查找用户的菜单
        //直接调用shiroService的实现方式，防止使用框架方需要自定义角色获取方式
        Set<String> roleNames = shiroService.getUserRoleNames(username);
        List<Permission> permissions = permissionRepository.findByRoleNameIn(roleNames);
        Map<Long, Resources> resourcesMap = new HashMap<>();
        permissions.forEach((p) -> {
            resourcesMap.put(p.getResource().getId(), p.getResource());
        });
        return resourcesMap.values();
    }

    @Override
    public Resources findById(Long id) {
        return resourcesRepository.findById(id);
    }

    /**
     * 根据parentId查找分页数据
     *
     * @param pageable 分页数据
     * @param parentId parentId
     * @return Page<Resources>
     */
    @Override
    public Page<Resources> findByParentId(Pageable pageable, Long parentId) {
        return resourcesRepository.findByParentId(pageable, parentId);
    }

    /**
     * 删除资源
     * 删除时，需要先删除权限以及子节点资源
     *
     * @param ids 要删的资源ID列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 60, propagation = Propagation.REQUIRED)
    public void deleteResource(Long[] ids) {
        for (Long i : ids) {
            //先删权限
            List<Permission> permissions = this.permissionRepository.findByResourceId(i);
            if (!permissions.isEmpty()) {
                this.permissionRepository.deleteAll(permissions);
            }
            //删除子节点
            List<Resources> resources = this.resourcesRepository.findByParentId(i);
            if (!resources.isEmpty()) {
                this.resourcesRepository.deleteAll(resources);
            }
            //删除自己
            this.resourcesRepository.deleteById(i);


            // 删除资源日志记录
            String optUserName = (String) SecurityUtils.getSubject().getPrincipal();
            this.shiroEventListener.deleteResourceEvent(optUserName, ids);
        }
    }
}
