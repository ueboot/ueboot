/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-21 09:40:34
*/
package com.ueboot.shiro.repository.role;

import com.ueboot.shiro.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
* 1.这里可以写基于StringQuery方式的自定义的接口，但是需要在实现类RoleRepositoryImpl当中进行实现，
* 2.如果使用Spring Data JPA语法，则写到RoleRepository接口当中
* 3.Service类注入接口时直接注入 RoleRepository接口即可，当前接口不需要注入
* Created on 2018-08-21 09:40:34
* @author yangkui
* @since 2.1.0 by ueboot-generator
*/
public interface RoleBaseRepository  {


    Page<Role> findByNameLike(Pageable pageable, String name);

}