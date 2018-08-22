/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-22 19:58:32
*/
package com.ueboot.shiro.controller.resources.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.*;

/**
 * 用于前端发起对象保存和更新请求时，接收请求参数
 * Created on 2018-08-22 19:58:32
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Setter
@Getter
@NoArgsConstructor
public class ResourcesReq {
    private Long id;
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    @NotBlank
    private String resourceType;
    @NotBlank
    private String url;
    @NotBlank
    private String themeJson;
    @NotBlank
    private String permission;
    @NotBlank
    private String parentCode;
    @NotBlank
    private String parentPath;
    private Long rank;
    private Long level;
    private Boolean available;
}