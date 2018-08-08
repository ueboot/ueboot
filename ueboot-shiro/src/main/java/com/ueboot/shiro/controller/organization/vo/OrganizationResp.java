/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-08 11:53:52
*/
package com.ueboot.shiro.controller.organization.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
* 用于前端发起查询请求时，返回查询结果
* Created on 2018-08-08 11:53:52
* @author yangkui
* @since 2.1.0 by ueboot-generator
*/
@Setter
@Getter
@NoArgsConstructor
public class OrganizationResp {
    private Long id;
    private String code;
    private String name;
    private String address;
    private String telephone;
    private String type;
    private String parentCode;
    private String parentPath;
    private Long level;
    private Boolean available;
}