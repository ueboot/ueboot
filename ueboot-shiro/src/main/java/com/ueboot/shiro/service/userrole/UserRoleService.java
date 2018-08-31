/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-21 09:39:51
*/
package com.ueboot.shiro.service.userrole;

import com.ueboot.shiro.entity.UserRole;
import com.ueboot.core.service.BaseService;

/**
 * Created on 2018-08-21 09:39:51
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
public interface UserRoleService extends BaseService<UserRole> {


    void saveUserRole(Long userId,Long[] roleIds);

}
