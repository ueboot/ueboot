package com.ueboot.shiro.controller.api;


import com.alibaba.fastjson.JSON;
import com.ueboot.core.exception.BusinessException;
import com.ueboot.core.http.response.Response;
import com.ueboot.core.utils.CaptchaUtils;
import com.ueboot.shiro.entity.Resources;
import com.ueboot.shiro.entity.User;
import com.ueboot.shiro.service.resources.ResourcesService;
import com.ueboot.shiro.service.user.UserService;
import com.ueboot.shiro.shiro.ShiroService;
import com.ueboot.shiro.shiro.processor.ShiroProcessor;
import com.ueboot.shiro.util.PasswordUtil;
import jodd.datetime.JDateTime;
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
import java.util.*;

/**
 * ueboot-shiro 对外提供Api接口（用户登录、退出、验证码、用户菜单、）
 *
 * @author yangkui
 */
@Slf4j
@RestController
@RequestMapping(value = "/ueboot/shiro")
public class ApiController {

    private static final String CAPTCHA_KEY = "UEBOOT_SHIRO_CAPTCHA_CODE";

    private final ShiroProcessor shiroProcessor;

    private final ResourcesService resourcesService;

    private final UserService userService;

    private final ShiroService shiroService;

    @Autowired
    public ApiController(ShiroProcessor shiroProcessor, ResourcesService resourcesService,
                         UserService userService, ShiroService shiroService) {
        this.shiroProcessor = shiroProcessor;
        this.resourcesService = resourcesService;
        this.userService = userService;
        this.shiroService = shiroService;
    }

    @PostMapping(value = "/public/login")
    public Response<Map<String, Object>> login(@RequestBody LoginVo params, HttpServletRequest request) {
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
        //返回登录成功后的信息
        Map<String, Object> info = this.shiroService.getLoginSuccessInfo(params.getUsername());
        return new Response<>(info);
    }

    @PostMapping(value = "/private/logout")
    public Response<Void> logout(@RequestBody LoginVo params) {
        log.info("/logout  username: {} ", params.getUsername(), params.getPassword(), params.getCaptcha());
        this.shiroProcessor.logout();
        return new Response<>();
    }


    @RequiresAuthentication
    @RequestMapping(value = "/private/updatePassword")
    public Response<Void> updatePassword(@RequestBody UpdatePasswordReq req) {
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        //加密旧密码
        String oldPassword = PasswordUtil.sha512(userName, req.getOldPassword().toLowerCase());
        //加密新密码
        String newPassword = PasswordUtil.sha512(userName, req.getNewPassword().toLowerCase());
        User user = userService.findByUserName(userName);
        if (!user.getPassword().equals(oldPassword)) {
            throw new BusinessException("原密码输入错误,请重新输入");
        }
        user.setPassword(newPassword);
        JDateTime dateTime = new JDateTime();
        //默认密码过期日期为x个月，x个月后要求更换密码
        Date expiredDate = dateTime.addMonth(this.shiroService.getPasswordExpiredMonth()).convertToDate();
        user.setCredentialExpiredDate(expiredDate);
        this.userService.save(user);
        return new Response<Void>();
    }


    /**
     * 获取登录用户的菜单资源
     *
     * @return 菜单资源
     */
    @RequiresAuthentication
    @RequestMapping(value = "/private/menus", method = RequestMethod.GET)
    public Response<List<MenuVo>> menus() {
        Subject currentUser = SecurityUtils.getSubject();
        String username = (String) currentUser.getPrincipal();

        Collection<Resources> resources = this.resourcesService.getUserResources(username);
        //查询出所有菜单组资源。防止授权时未勾选菜单组，导致前端页面没有菜单出现
        List<Resources> groups = this.resourcesService.findByResourceType(Resources.RESOURCE_TYPE_GROUP);
        List<MenuVo> body = new ArrayList<>();
        List<Resources> parents = new ArrayList<>();
        Map<Long, Resources> resourcesMap = new HashMap<>();
        for (Resources resource : resources) {
            if (Resources.RESOURCE_TYPE_BUTTON.equals(resource.getResourceType())) {
                continue;
            }
            if (resource.getParent() != null) {
                parents.add(resource.getParent());
            }
            resourcesMap.put(resource.getId(), resource);
            body.add(assembleMenuVo(resource));
        }
        //查找所有父节点是否在结果集当中，不在则需要获取
        parents.forEach((p) -> {
            Resources parent = resourcesMap.get(p.getId());
            if (parent == null) {
                while (groups.iterator().hasNext()) {
                    Resources g = groups.iterator().next();
                    if (p.getId().equals(g.getId())) {
                        body.add(assembleMenuVo(g));
                        groups.remove(g);
                    }
                }
            }
        });
        return new Response<>(body);
    }

    private MenuVo assembleMenuVo(Resources resource) {
        MenuVo menu = new MenuVo();
        BeanUtils.copyProperties(resource, menu);
        if (resource.getParent() != null) {
            menu.setParentId(resource.getParent().getId());
        }
        menu.setThemeJson(StringUtils.isEmpty(resource.getThemeJson()) ? new HashMap() : JSON.parseObject(resource.getThemeJson(), Map.class));
        return menu;
    }

    /**
     * 获取验证码
     *
     * @param request  request
     * @param response response
     * @throws IOException IOException
     */
    @RequestMapping(value = "/public/captcha", method = RequestMethod.GET)
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        response.setContentType("image/jpeg");
        String captcha = CaptchaUtils.generate(4);
        HttpSession session = request.getSession();
        session.setAttribute(CAPTCHA_KEY, captcha.toLowerCase());
        int w = 200;
        int h = 80;
        CaptchaUtils.outputImage(w, h, response.getOutputStream(), captcha);
    }

}
