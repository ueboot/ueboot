/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-08 14:05:30
*/
package com.ueboot.shiro.repository.permission;

import com.ueboot.shiro.entity.Permission;
import com.ueboot.core.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
* 这个类里面使用spring data jpa 方式实现数据库的CRUD
* Created on 2018-08-08 14:05:30
* @author yangkui
* @since 2.1.0 by ueboot-generator
*/
@Repository
public interface PermissionRepository extends BaseRepository<Permission, Long>,PermissionBaseRepository {

}
