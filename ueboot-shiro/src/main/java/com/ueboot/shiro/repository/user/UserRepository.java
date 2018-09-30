/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-14 10:47:54
*/
package com.ueboot.shiro.repository.user;

import com.ueboot.shiro.entity.User;
import com.ueboot.core.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
* 这个类里面使用spring data jpa 方式实现数据库的CRUD
* Created on 2018-08-14 10:47:54
* @author yangkui
* @since 2.1.0 by ueboot-generator
*/
@Repository
public interface UserRepository extends BaseRepository<User, Long>,UserBaseRepository {
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


    /**
     * 用户查询
     * @param pageable
     * @param userName
     * @param fullName
     */
    Page<User> pageByUserNameAndFullName(Pageable pageable, String userName, String fullName);
}
