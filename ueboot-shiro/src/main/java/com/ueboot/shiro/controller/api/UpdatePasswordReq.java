package com.ueboot.shiro.controller.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author yangkui
 *
 */
@Getter
@Setter
@NoArgsConstructor
class UpdatePasswordReq {
    /**
     * 原来的密码
     */
    private String oldPassword;
    /**
     * 新密码
     */
    private String newPassword;

}
