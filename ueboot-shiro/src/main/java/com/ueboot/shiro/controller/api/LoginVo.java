package com.ueboot.shiro.controller.api;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginVo {

    private String username;

    private String password;

    private String captcha;

}
