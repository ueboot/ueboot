package com.ueboot.core.controller;

import com.ueboot.core.utils.CaptchaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * ueboot-core 对外提供Api接口（生成验证码）
 *
 * @author yangkui
 */
@Slf4j
@RestController
@RequestMapping(value = "/ueboot/core")
public class UeBootController {

    /**
     * 获取验证码
     *
     * @param sessionKey 保存到session当中的key,校验时需要使用
     * @param width 验证码宽度
     * @param height 验证码高度
     * @param length  验证码长度
     * @param request  request
     * @param response response
     * @throws IOException IOException
     */
    @RequestMapping(value = "/public/captcha/{sessionKey}/{width}/{height}/{length}", method = RequestMethod.GET)
    public void captcha(@PathVariable String sessionKey, @PathVariable(required = false)
            int width,@PathVariable(required = false) int height, @PathVariable(required = false) int length, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        response.setContentType("image/jpeg");
        String captcha = CaptchaUtils.generate(length ==0 ?4:length);
        HttpSession session = request.getSession(true);
        session.setAttribute(sessionKey, captcha.toLowerCase());
        if(width>200){
            width = 200;
        }
        if(height>100){
            height=100;
        }
        int w = width ==0?200:width;
        int h = height==0?80:height;
        CaptchaUtils.outputImage(w, h, response.getOutputStream(), captcha);
    }
}
