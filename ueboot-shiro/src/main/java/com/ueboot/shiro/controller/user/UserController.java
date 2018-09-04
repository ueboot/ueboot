/*
 * Copyright (c)  2018
 * All rights reserved.
 * 2018-08-14 10:47:55
 */
package com.ueboot.shiro.controller.user;

import com.ueboot.core.exception.BusinessException;
import com.ueboot.core.http.annotation.PageableLimits;
import com.ueboot.core.http.response.Response;
import com.ueboot.shiro.controller.user.vo.UserFindReq;
import com.ueboot.shiro.controller.user.vo.UserReq;
import com.ueboot.shiro.controller.user.vo.UserResp;
import com.ueboot.shiro.entity.User;
import com.ueboot.shiro.service.user.UserService;
import com.ueboot.shiro.shiro.ShiroService;
import com.ueboot.shiro.shiro.UserRealm;
import com.ueboot.shiro.util.PasswordUtil;
import jodd.datetime.JDateTime;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;


/**
 * Created on 2018-08-14 10:47:55
 *
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Slf4j
@RestController
@RequestMapping(value = "/ueboot/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private ShiroService shiroService;


    @RequiresPermissions("ueboot:user:read")
    @PostMapping(value = "/page")
    public Response<Page<UserResp>> page(@PageableLimits(maxSize = 10000)
                                         @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.ASC)
                                                 Pageable pageable, @RequestBody(required = false) UserFindReq req) {
        Page<User> entities = userService.findBy(pageable);
        Page<UserResp> body = entities.map(entity -> {
            UserResp resp = new UserResp();
            BeanUtils.copyProperties(entity, resp);
            return resp;
        });

        return new Response<>(body);
    }


    @RequiresPermissions("ueboot:user:save")
    @PostMapping(value = "/save")
    public Response<Void> save(@RequestBody UserReq req) {
        User entity = null;
        if (req.getId() == null) {
            entity = new User();
            User user = this.userService.findByUserName(req.getUserName());
            if (user != null) {
                throw new BusinessException("当前用户名已经存在，不能重复添加!");
            }
        } else {
            entity = userService.findById(req.getId());
        }

        BeanUtils.copyProperties(req, entity, "password");
        if (StringUtil.isNotBlank(req.getPassword())) {
            entity.setPassword(PasswordUtil.sha512(entity.getUserName(), req.getPassword()));
            if (req.getCredentialExpiredDate() == null) {
                JDateTime dateTime = new JDateTime();
                //默认密码过期日期为x个月，x个月后要求更换密码
                Date expiredDate = dateTime.addMonth(this.shiroService.getPasswordExpiredMonth()).convertToDate();
                entity.setCredentialExpiredDate(expiredDate);
            }
        }
        userService.save(entity);
        return new Response<>();
    }


    @RequiresPermissions("ueboot:user:delete")
    @PostMapping(value = "/delete")
    public Response<Void> delete(Long[] id) {
        userService.delete(id);
        return new Response<>();
    }

    @RequiresPermissions("ueboot:user:read")
    @GetMapping(value = "/{id}")
    public Response<UserResp> get(@PathVariable Long id) {
        User entity = userService.get(id);
        UserResp resp = new UserResp();
        BeanUtils.copyProperties(entity, resp);
        return new Response<>(resp);
    }

}
