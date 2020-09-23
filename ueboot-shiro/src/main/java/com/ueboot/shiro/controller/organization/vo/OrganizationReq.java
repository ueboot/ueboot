/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-08 11:53:52
*/
package com.ueboot.shiro.controller.organization.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 用于前端发起对象保存和更新请求时，接收请求参数
 * Created on 2018-08-08 11:53:52
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Setter
@Getter
@NoArgsConstructor
public class OrganizationReq {
    private Long id;
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotBlank
    private String telephone;
    @NotBlank
    private String type;
    @NotBlank
    private String parentCode;
    @NotBlank
    private String parentPath;
    private Long level;
    private Boolean available;
}