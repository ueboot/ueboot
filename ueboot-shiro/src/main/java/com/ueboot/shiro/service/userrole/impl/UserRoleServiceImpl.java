/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-21 09:39:51
*/
package com.ueboot.shiro.service.userrole.impl;

import com.ueboot.shiro.entity.UserRole;
import com.ueboot.core.repository.BaseRepository;
import com.ueboot.shiro.repository.userrole.UserRoleRepository;
import com.ueboot.core.service.impl.BaseServiceImpl;
import com.ueboot.shiro.service.userrole.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2018-08-21 09:39:51
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Slf4j
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements UserRoleService{
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    protected BaseRepository getBaseRepository() {
         return userRoleRepository;
    }
}
