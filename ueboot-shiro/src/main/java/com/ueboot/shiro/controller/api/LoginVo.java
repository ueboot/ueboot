package com.ueboot.shiro.controller.api;

import com.ueboot.core.annotation.NotLog;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@NotLog
public class LoginVo {

    private String username;

    private String password;

    private String captcha;

}
