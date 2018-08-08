/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-08 14:06:03
*/
package com.ueboot.shiro.service.resources.impl;

import com.ueboot.shiro.entity.Resources;
import com.ueboot.core.repository.BaseRepository;
import com.ueboot.shiro.repository.resources.ResourcesRepository;
import com.ueboot.core.service.impl.BaseServiceImpl;
import com.ueboot.shiro.service.resources.ResourcesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2018-08-08 14:06:03
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Slf4j
@Service
public class ResourcesServiceImpl extends BaseServiceImpl<Resources> implements ResourcesService{
    @Autowired
    private ResourcesRepository resourcesRepository;

    @Override
    protected BaseRepository getBaseRepository() {
         return resourcesRepository;
    }
}
