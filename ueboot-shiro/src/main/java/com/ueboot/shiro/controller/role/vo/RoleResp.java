/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-21 09:40:34
*/
package com.ueboot.shiro.controller.role.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
* 用于前端发起查询请求时，返回查询结果
* Created on 2018-08-21 09:40:34
* @author yangkui
* @since 2.1.0 by ueboot-generator
*/
@Setter
@Getter
@NoArgsConstructor
public class RoleResp {
    private Long id;
    private String code;
    private String orgCode;
    private String role;
    private String name;
    private String description;
    private Boolean available;
}