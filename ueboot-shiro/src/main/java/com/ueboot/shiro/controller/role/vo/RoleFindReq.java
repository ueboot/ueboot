/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-21 09:40:34
*/
package com.ueboot.shiro.controller.role.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 用于前端发起查询请求时，接收请求参数
 * Created on 2018-08-21 09:40:34
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Setter
@Getter
@NoArgsConstructor
public class RoleFindReq {
    private Long id;
    private String code;
    private String orgCode;
    private String role;
    @NotBlank
    private String name;
    private String description;
    private Boolean available;
}