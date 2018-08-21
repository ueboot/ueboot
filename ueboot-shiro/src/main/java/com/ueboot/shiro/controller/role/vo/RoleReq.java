/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-21 09:40:34
*/
package com.ueboot.shiro.controller.role.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.*;

/**
 * 用于前端发起对象保存和更新请求时，接收请求参数
 * Created on 2018-08-21 09:40:34
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Setter
@Getter
@NoArgsConstructor
public class RoleReq {
    private Long id;
    @NotBlank
    private String code;
    @NotBlank
    private String orgCode;
    @NotBlank
    private String role;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private Boolean available;
}