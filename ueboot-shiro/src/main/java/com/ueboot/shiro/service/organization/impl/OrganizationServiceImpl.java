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
}
