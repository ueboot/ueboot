/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-21 09:39:51
*/
package com.ueboot.shiro.controller.userrole.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.*;

/**
 * 用于前端发起对象保存和更新请求时，接收请求参数
 * Created on 2018-08-21 09:39:51
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Setter
@Getter
@NoArgsConstructor
public class UserRoleReq {
    private Long userId;
    private Long[] roleIds;
}