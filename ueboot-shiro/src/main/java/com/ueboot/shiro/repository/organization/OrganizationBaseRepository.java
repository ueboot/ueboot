/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-08 11:53:52
*/
package com.ueboot.shiro.repository.organization;

import com.ueboot.shiro.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
* 1.这里可以写基于StringQuery方式的自定义的接口，但是需要在实现类OrganizationRepositoryImpl当中进行实现，
* 2.如果使用Spring Data JPA语法，则写到OrganizationRepository接口当中
* 3.Service类注入接口时直接注入 OrganizationRepository接口即可，当前接口不需要注入
* Created on 2018-08-08 11:53:52
* @author yangkui
* @since 2.1.0 by ueboot-generator
*/
@Repository
public interface OrganizationBaseRepository  {

    /**
     * 根据关键字进行分页查询，关键字匹配机构名称、代码、地址、电话字段任意一个
     * @param pageable 分页对象
     * @param keyword 关键字
     * @return 分页查询结果
     */
    Page<Organization> findByKey(Pageable pageable,String keyword);

}