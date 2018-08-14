/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-08 14:06:03
*/
package com.ueboot.shiro.service.resources;

import com.ueboot.shiro.entity.Resources;
import com.ueboot.core.service.BaseService;

/**
 * Created on 2018-08-08 14:06:03
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
public interface ResourcesService extends BaseService<Resources> {
    /**
     * 根据用户名查找用户的授权的资源
     * @param username 用户名
     * @return 当前用户授权后的资源列表
     */
    Resources[] getUserResources(String username);
}
