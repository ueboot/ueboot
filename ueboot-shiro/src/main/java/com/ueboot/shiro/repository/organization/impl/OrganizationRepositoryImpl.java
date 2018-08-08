/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-08 11:53:52
*/
package com.ueboot.shiro.repository.organization.impl;

import com.ueboot.core.jpa.repository.query.StringQuery;
import com.ueboot.shiro.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import com.ueboot.core.jpa.repository.DefaultJpaRepository;
import com.ueboot.shiro.repository.organization.OrganizationBaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

/**
* 自定义接口实现类，可以使用父类DefaultJpaRepository当中的find(),findBySql等方法实现自定义的StringQuery查询
* 相关使用示例，参见文档http://docs.ueboot.com/jpa
* Created on 2018-08-08 11:53:52
* @author yangkui
* @since 2.1.0 by ueboot-generator
*/
@Slf4j
@Repository
public class OrganizationRepositoryImpl extends DefaultJpaRepository<Organization,Long> implements OrganizationBaseRepository {

    /**
     * 根据关键字进行分页查询，关键字匹配机构名称、代码、地址、电话字段任意一个
     *
     * @param pageable 分页对象
     * @param keyword  关键字
     * @return 分页查询结果
     */
    @Override
    public Page<Organization> findByKey(Pageable pageable, String keyword) {
        Assert.notNull(keyword,"查询关键字不能为空");

        StringQuery query = StringQuery.newQuery()
                .query("from "+Organization.class.getName()+" a ")
                .predicateHasText(keyword)
                .query(" a.name like =:keyword or a.address like =:keyword")
                .likeParam("keyword",keyword)
                .predicate(Boolean.TRUE).build();
        return find(query,pageable);
    }
}
