/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-08 11:53:52
*/
package com.ueboot.shiro.service.organization;

import com.ueboot.shiro.entity.Organization;
import com.ueboot.core.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created on 2018-08-08 11:53:52
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
public interface OrganizationService extends BaseService<Organization> {
    /**
     * 根据关键字进行分页查询，关键字匹配机构名称、代码、地址、电话字段任意一个
     * @param pageable 分页对象
     * @param keyword 关键字
     * @return 分页查询结果
     */
    Page<Organization> findByKey(Pageable pageable, String keyword);
}
