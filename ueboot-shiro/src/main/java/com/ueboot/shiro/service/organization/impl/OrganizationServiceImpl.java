/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-08 11:53:52
*/
package com.ueboot.shiro.service.organization.impl;

import com.ueboot.shiro.entity.Organization;
import com.ueboot.core.repository.BaseRepository;
import com.ueboot.shiro.repository.organization.OrganizationRepository;
import com.ueboot.core.service.impl.BaseServiceImpl;
import com.ueboot.shiro.service.organization.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created on 2018-08-08 11:53:52
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Slf4j
@Service
public class OrganizationServiceImpl extends BaseServiceImpl<Organization> implements OrganizationService{
    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    protected BaseRepository getBaseRepository() {
         return organizationRepository;
    }

    /**
     * 根据关键字进行分页查询，关键字匹配机构名称、代码、地址、电话字段任意一个
     *
     * @param pageable 分页对象
     * @param keyword  关键字
     * @return 分页查询结果
     */
    @Override
    public Page<Organization> findByKey(Pageable pageable, String keyword) {
        return organizationRepository.findByKey(pageable, keyword);
    }
}
