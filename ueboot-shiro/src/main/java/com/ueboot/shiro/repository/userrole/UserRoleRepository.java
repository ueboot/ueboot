/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-21 09:39:51
*/
package com.ueboot.shiro.repository.userrole;

import com.ueboot.shiro.entity.UserRole;
import com.ueboot.core.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 这个类里面使用spring data jpa 方式实现数据库的CRUD
* Created on 2018-08-21 09:39:51
* @author yangkui
* @since 2.1.0 by ueboot-generator
*/
@Repository
public interface UserRoleRepository extends BaseRepository<UserRole, Long>,UserRoleBaseRepository {

    /**
     * 根据用户名称查找用户所属的角色列表
     * @param userName 用户名称
     * @return 角色列表
     */
    List<UserRole> findByUserUserName(String userName);

    /**
     * 根据用户ID查询用户所属角色
     * @param userId 用户ID
     * @return 角色列表
     */
    List<UserRole> findByUserUserId(Long userId);


}
