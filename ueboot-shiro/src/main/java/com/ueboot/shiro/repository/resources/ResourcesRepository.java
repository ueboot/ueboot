/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-22 19:58:32
*/
package com.ueboot.shiro.repository.resources;

import com.ueboot.shiro.entity.Resources;
import com.ueboot.core.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 这个类里面使用spring data jpa 方式实现数据库的CRUD
* Created on 2018-08-22 19:58:32
* @author yangkui
* @since 2.1.0 by ueboot-generator
*/
@Repository
public interface ResourcesRepository extends BaseRepository<Resources, Long>,ResourcesBaseRepository {

    List<Resources> findByParentId(Long parentId);

    /**
     * 根据资源类型查找资源列表
     * @param resourcesType 资源类型
     * @return 资源列表
     */
    List<Resources> findByResourceType(String resourcesType);


}
