/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-14 10:47:55
*/
package com.ueboot.shiro.service.user;

import com.ueboot.shiro.entity.User;
import com.ueboot.core.service.BaseService;

/**
 * Created on 2018-08-14 10:47:55
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
public interface UserService extends BaseService<User> {

    /**
     * 根据用户名查找用户
     * @param userName 用户名
     * @return 用户，不存在则返回空对象
     */
    User findByUserName(String userName);

    /**
     * 根据ID查找用户
     * @param id 主键ID
     * @return 用户对象
     */
    User findById(Long id);

    void deleteById(Long[] id);

}
