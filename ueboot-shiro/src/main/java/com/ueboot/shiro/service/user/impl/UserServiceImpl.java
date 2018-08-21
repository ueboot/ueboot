/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-14 10:47:55
*/
package com.ueboot.shiro.service.user.impl;

import com.ueboot.shiro.entity.User;
import com.ueboot.core.repository.BaseRepository;
import com.ueboot.shiro.repository.user.UserRepository;
import com.ueboot.core.service.impl.BaseServiceImpl;
import com.ueboot.shiro.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2018-08-14 10:47:55
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    protected BaseRepository getBaseRepository() {
         return userRepository;
    }

    /**
     * 根据用户名和密码查找用户
     *
     * @param userName 用户名
     * @param password 密码
     * @return 用户，不存在则返回空对象
     */
    @Override
    public User findByUserNameAndPassword(String userName, String password) {
        return userRepository.findByUserNameAndPassword(userName,password);
    }
}
