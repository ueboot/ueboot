/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-21 09:40:34
*/
package com.ueboot.shiro.repository.role;

import com.ueboot.shiro.entity.Role;
import com.ueboot.core.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
* 这个类里面使用spring data jpa 方式实现数据库的CRUD
* Created on 2018-08-21 09:40:34
* @author yangkui
* @since 2.1.0 by ueboot-generator
*/
@Repository
public interface RoleRepository extends BaseRepository<Role, Long>,RoleBaseRepository {

}
