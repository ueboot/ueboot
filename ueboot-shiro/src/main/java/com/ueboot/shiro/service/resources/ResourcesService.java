/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-08 14:06:03
*/
package com.ueboot.shiro.service.resources;

import com.ueboot.core.service.BaseService;
import com.ueboot.shiro.entity.Resources;
import com.ueboot.shiro.repository.permission.bo.PermissionBo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

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
    Collection<PermissionBo> getUserResources(String username);

    /**
     * 根据资源类型查找资源列表
     * @param resourcesType 资源类型
     * @return 资源列表
     */
    List<Resources> findByResourceType(String resourcesType);

    Resources findById(Long id);

    /**
     * 根据parentId查找分页数据
     * @param pageable 分页数据
     * @param parentId parentId
     * @return Page<Resources>
     */
    Page<Resources> findByParentId(Pageable pageable,Long parentId);

    /**
     * 删除资源
     * 删除时，需要先删除权限以及子节点资源
     * @param ids 要删的资源ID列表
     */
    void deleteResource(Long[] ids);
}
