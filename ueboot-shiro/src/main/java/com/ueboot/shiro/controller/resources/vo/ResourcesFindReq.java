/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-22 19:58:32
*/
package com.ueboot.shiro.controller.resources.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 用于前端发起查询请求时，接收请求参数
 * Created on 2018-08-22 19:58:32
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Setter
@Getter
@NoArgsConstructor
public class ResourcesFindReq {
    private Long parentId;
}