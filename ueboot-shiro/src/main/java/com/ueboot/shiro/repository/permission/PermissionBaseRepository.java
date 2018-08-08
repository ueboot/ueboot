/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-08 14:05:30
*/
package com.ueboot.shiro.repository.permission;

import org.springframework.stereotype.Repository;

/**
* 1.这里可以写基于StringQuery方式的自定义的接口，但是需要在实现类PermissionRepositoryImpl当中进行实现，
* 2.如果使用Spring Data JPA语法，则写到PermissionRepository接口当中
* 3.Service类注入接口时直接注入 PermissionRepository接口即可，当前接口不需要注入
* Created on 2018-08-08 14:05:30
* @author yangkui
* @since 2.1.0 by ueboot-generator
*/
@Repository
public interface PermissionBaseRepository  {

}