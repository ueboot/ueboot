/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-21 09:40:34
*/
package com.ueboot.shiro.service.role.impl;

import com.ueboot.shiro.entity.Role;
import com.ueboot.core.repository.BaseRepository;
import com.ueboot.shiro.repository.role.RoleRepository;
import com.ueboot.core.service.impl.BaseServiceImpl;
import com.ueboot.shiro.service.role.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2018-08-21 09:40:34
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Slf4j
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    protected BaseRepository getBaseRepository() {
         return roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
