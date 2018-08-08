/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-08 14:06:03
*/
package com.ueboot.shiro.controller.resources.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 用于前端发起查询请求时，接收请求参数
 * Created on 2018-08-08 14:06:03
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Setter
@Getter
@NoArgsConstructor
public class ResourcesFindReq {
    private Long id;
    private String code;
    private String name;
    private String resourceType;
    private String url;
    private String themeJson;
    private String permission;
    private String parentCode;
    private String parentPath;
    private Long rank;
    private Long level;
    private Boolean available;
}