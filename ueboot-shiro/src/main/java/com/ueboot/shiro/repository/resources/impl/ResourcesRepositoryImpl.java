/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-22 19:58:32
*/
package com.ueboot.shiro.repository.resources.impl;

import com.ueboot.core.jpa.repository.query.StringQuery;
import com.ueboot.shiro.entity.Resources;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.ueboot.core.jpa.repository.DefaultJpaRepository;
import com.ueboot.shiro.repository.resources.ResourcesBaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

/**
* 自定义接口实现类，可以使用父类DefaultJpaRepository当中的find(),findBySql等方法实现自定义的StringQuery查询
* 相关使用示例，参见文档http://docs.ueboot.com
* Created on 2018-08-22 19:58:32
* @author yangkui
* @since 2.1.0 by ueboot-generator
*/
@Slf4j
@Repository
public class ResourcesRepositoryImpl extends DefaultJpaRepository<Resources,Long> implements ResourcesBaseRepository {

    /**
     * 根据parentId查找分页数据
     *
     * @param pageable 分页数据
     * @param parentId parentId
     * @return Page<Resources>
     */
    @Override
    public Page<Resources> findByParentId(Pageable pageable, Long parentId) {
        StringQuery query  = StringQuery.newQuery();
        query.query("from "+Resources.class.getName()+" a where (1=1) ")
                .predicateNotNull(parentId)
                .query(" and a.parent.id = :parentId or a.id = :parentId")
                .param("parentId",parentId)
                .predicateIsNull(parentId)
                .query(" and a.parent.id is null")
                .predicate(true).build();
        return find(query,pageable);
    }
}
