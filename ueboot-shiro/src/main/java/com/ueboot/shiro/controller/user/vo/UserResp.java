/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-14 10:47:55
*/
package com.ueboot.shiro.controller.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
    private String userName;
    private String roleNames;
    private String roleIds;
    private boolean locked;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date credentialExpiredDate;
}