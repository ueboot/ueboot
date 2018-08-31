/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-21 09:39:51
*/
package com.ueboot.shiro.service.userrole.impl;

import com.ueboot.shiro.entity.Role;
import com.ueboot.shiro.entity.User;
import com.ueboot.shiro.entity.UserRole;
import com.ueboot.core.repository.BaseRepository;
import com.ueboot.shiro.repository.role.RoleRepository;
import com.ueboot.shiro.repository.user.UserRepository;
import com.ueboot.shiro.repository.userrole.UserRoleRepository;
import com.ueboot.core.service.impl.BaseServiceImpl;
import com.ueboot.shiro.service.userrole.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * Created on 2018-08-21 09:39:51
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Slf4j
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements UserRoleService{
    @Resource
    private UserRoleRepository userRoleRepository;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private UserRepository userRepository;

    @Override
    protected BaseRepository getBaseRepository() {
         return userRoleRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void saveUserRole(Long userId, Long[] roleIds) {
        //删除原有数据
        List<UserRole> roles = userRoleRepository.findByUserUserId(userId);
        if(!roles.isEmpty()){
            this.userRoleRepository.deleteInBatch(new Iterable<UserRole>() {
                @Override
                public Iterator<UserRole> iterator() {
                    return roles.iterator();
                }
            });
        }
        //插入新数据
        User user = new User();
        user.setId(userId);
        StringBuilder roleNames = new StringBuilder();
        for (int i = 0; i < roleIds.length; i++) {
            Long roleId = roleIds[i];
            Role role = roleRepository.getOne(roleId);
            roleNames.append(role.getName()).append(",");
            role.setId(roleId);
            UserRole userRole = new UserRole();
            userRole.setRole(role);
            userRole.setUser(user);
            this.userRoleRepository.save(userRole);
        }
        user.setRoleNames(roleNames.toString());
        userRepository.save(user);
    }
}
