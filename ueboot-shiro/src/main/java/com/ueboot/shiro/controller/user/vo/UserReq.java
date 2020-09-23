/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-14 10:47:55
*/
package com.ueboot.shiro.controller.user.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 用于前端发起对象保存和更新请求时，接收请求参数
 * Created on 2018-08-14 10:47:55
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Setter
@Getter
@NoArgsConstructor
public class UserReq {
    private Long id;

    @NotBlank
    private String userName;
    private String password;
    private boolean locked;
    private Date credentialExpiredDate;

}