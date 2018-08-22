/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-22 10:24:03
*/
package com.ueboot.shiro.service.permission.impl;

import com.ueboot.shiro.entity.Permission;
import com.ueboot.core.repository.BaseRepository;
import com.ueboot.shiro.repository.permission.PermissionRepository;
import com.ueboot.core.service.impl.BaseServiceImpl;
import com.ueboot.shiro.service.permission.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2018-08-22 10:24:03
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Slf4j
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService{
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    protected BaseRepository getBaseRepository() {
         return permissionRepository;
    }
}
