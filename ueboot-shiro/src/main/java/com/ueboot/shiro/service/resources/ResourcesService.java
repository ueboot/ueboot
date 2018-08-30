/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-08 14:06:03
*/
package com.ueboot.shiro.service.resources;

import com.ueboot.shiro.entity.Resources;
import com.ueboot.core.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    Resources findById(Long id);

    /**
     * 根据parentId查找分页数据
     * @param pageable 分页数据
     * @param parentId parentId
     * @return Page<Resources>
     */
    Page<Resources> findByParentId(Pageable pageable,Long parentId);
}
