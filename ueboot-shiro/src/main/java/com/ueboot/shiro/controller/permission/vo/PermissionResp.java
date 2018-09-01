/*
 * Copyright (c)  2018
 * All rights reserved.
 * 2018-08-08 14:05:30
 */
package com.ueboot.shiro.controller.permission.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用于前端发起查询请求时，返回查询结果
 * Created on 2018-08-08 14:05:30
 *
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Setter
@Getter
@NoArgsConstructor
public class PermissionResp {
    private Long id;
    private Long roleId;
    private Long resourceId;
}