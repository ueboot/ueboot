/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-14 10:47:55
*/
package com.ueboot.shiro.controller.user.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
* 用于前端发起查询请求时，返回查询结果
* Created on 2018-08-14 10:47:55
* @author yangkui
* @since 2.1.0 by ueboot-generator
*/
@Setter
@Getter
@NoArgsConstructor
public class UserResp {
    private Long id;
    private String orgCode;
    private String username;
    private String password;
}