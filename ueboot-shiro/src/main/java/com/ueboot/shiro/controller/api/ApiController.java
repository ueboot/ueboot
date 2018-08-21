package com.ueboot.shiro.controller.api;


import com.alibaba.fastjson.JSON;
import com.ueboot.core.http.response.Response;
import com.ueboot.core.utils.CaptchaUtils;
import com.ueboot.shiro.entity.Resources;
import com.ueboot.shiro.service.resources.ResourcesService;
import com.ueboot.shiro.shiro.processor.ShiroProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ueboot-shiro 对外提供Api接口（用户登录、退出、验证码、用户菜单、）
 * @author yangkui
 */
@Slf4j
@RestController
@RequestMapping(value = "/ueboot/shiro")
public class ApiController {

    private static final String CAPTCHA_KEY = "UEBOOT_SHIRO_CAPTCHA_CODE";

    private final ShiroProcessor shiroProcessor;

    private final ResourcesService resourcesService;

    @Autowired
    public ApiController(ShiroProcessor shiroProcessor, ResourcesService resourcesService) {
        this.shiroProcessor = shiroProcessor;
        this.resourcesService = resourcesService;
    }

    @PostMapping(value = "/public/login")
    public Response<Void> login(@RequestBody LoginVo params, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String sessionCaptcha = (String) session.getAttribute(CAPTCHA_KEY);
        log.debug("从session当中获取的验证码:{},用户提交的验证码:{}", sessionCaptcha, params.getCaptcha());
        if (sessionCaptcha != null && params.getCaptcha().toLowerCase().equals(sessionCaptcha.toLowerCase())) {
            session.setAttribute(CAPTCHA_KEY, "");
        } else {
            session.setAttribute(CAPTCHA_KEY, "");
            throw new IllegalArgumentException("验证码不正确!");
        }
        this.shiroProcessor.login(params.getUsername(), params.getPassword());
        return new Response<>();
    }
    @RequiresAuthentication
    @PostMapping(value = "/private/logout")
    public Response<Void> logout(@RequestBody LoginVo params) {
        log.info("/logout  username: {} ", params.getUsername(), params.getPassword(), params.getCaptcha());
        this.shiroProcessor.logout();
        return new Response<>();
    }

    /**
     * 获取登录用户的菜单资源
     * @return 菜单资源
     */
    @RequiresAuthentication
    @RequestMapping(value = "/private/menus", method = RequestMethod.GET)
    public Response<List<MenuVo>> menus() {
        Subject currentUser = SecurityUtils.getSubject();
        String username = (String) currentUser.getPrincipal();

        Resources[] resources = this.resourcesService.getUserResources(username);

        List<MenuVo> body = new ArrayList<>();

        for (Resources resource : resources) {
            MenuVo menu = new MenuVo();
            BeanUtils.copyProperties(resource, menu);
            menu.setThemeJson(StringUtils.isEmpty(resource.getThemeJson()) ? new HashMap() : JSON.parseObject(resource.getThemeJson(), Map.class));
            body.add(menu);
        }
        return new Response<>(body);
    }

    /**
     * 获取验证码
     * @param request request
     * @param response response
     * @throws IOException IOException
     */
    @RequestMapping(value = "/public/captcha", method = RequestMethod.GET)
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        response.setContentType("image/jpeg");
        String captcha = CaptchaUtils.generate(4);
        HttpSession session = request.getSession(true);
        session.setAttribute(CAPTCHA_KEY, captcha.toLowerCase());
        int w = 200;
        int h = 80;
        CaptchaUtils.outputImage(w, h, response.getOutputStream(), captcha);
    }

}
