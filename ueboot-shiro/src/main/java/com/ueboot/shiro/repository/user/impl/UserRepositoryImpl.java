/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-14 10:47:54
*/
package com.ueboot.shiro.repository.user.impl;

import com.ueboot.core.jpa.repository.query.StringQuery;
import com.ueboot.shiro.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.ueboot.core.jpa.repository.DefaultJpaRepository;
import com.ueboot.shiro.repository.user.UserBaseRepository;
import lombok.extern.slf4j.Slf4j;

/**
* 自定义接口实现类，可以使用父类DefaultJpaRepository当中的find(),findBySql等方法实现自定义的StringQuery查询
* 相关使用示例，参见文档http://docs.ueboot.com
* Created on 2018-08-14 10:47:54
* @author yangkui
* @since 2.1.0 by ueboot-generator
*/
@Slf4j
@Repository
public class UserRepositoryImpl extends DefaultJpaRepository<User,Long> implements UserBaseRepository {


    @Override
    public Page<User> pageByUserNameAndFullName(Pageable pageable, String userName, String fullName) {

        StringQuery query = StringQuery.newQuery()
                .query(" FROM " + User.class.getName() + " u WHERE 1=1 ")

                .predicateHasText(userName)
                .query(" AND userName like :userName")
                .likeParam("userName", userName)

                .predicateHasText(fullName)
                .query(" AND fullName like :fullName")
                .likeParam("fullName", fullName)

                .predicate(true)
                .build();


        return find(query, pageable);
    }
}
