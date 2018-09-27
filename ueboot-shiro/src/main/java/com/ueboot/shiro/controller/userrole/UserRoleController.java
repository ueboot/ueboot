/*
 * Copyright (c)  2018
 * All rights reserved.
 * 2018-08-21 09:39:51
 */
package com.ueboot.shiro.controller.userrole;

import com.ueboot.core.http.response.Response;
import com.ueboot.shiro.controller.userrole.vo.*;
import com.ueboot.shiro.entity.UserRole;
import com.ueboot.shiro.service.userrole.UserRoleService;
import com.ueboot.shiro.shiro.ShiroEventListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * Created on 2018-08-21 09:39:51
 *
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Slf4j
@RestController
@RequestMapping(value = "/ueboot/userRole")
public class UserRoleController {

    @Resource
    private UserRoleService userRoleService;

    // shiro 权限日志
    @Resource
    private ShiroEventListener shiroEventListener;

    @RequiresPermissions("ueboot:userRole:read")
    @PostMapping(value = "/page")
    public Response<Page<UserRoleResp>> page(@PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC)
                                                     Pageable pageable, @RequestBody(required = false) UserRoleFindReq req) {
        Page<UserRole> entities = userRoleService.findBy(pageable);
        Page<UserRoleResp> body = entities.map(entity -> {
            UserRoleResp resp = new UserRoleResp();
            BeanUtils.copyProperties(entity, resp);
            return resp;
        });

        return new Response<>(body);
    }

    @RequiresPermissions("ueboot:userRole:read")
    @PostMapping(value = "/findByRoleId")
    public Response<List<UserRoleResp>> findByUserId(@RequestBody UserRoleReq req) {
        List<UserRole> users = userRoleService.findByUserId(req.getUserId());
        List<UserRoleResp> result = new ArrayList<>();
        users.forEach((u) -> {
            UserRoleResp resp = new UserRoleResp();
            BeanUtils.copyProperties(u, resp);
            result.add(resp);
        });
        return new Response<>(result);
    }

    @RequiresPermissions("ueboot:userRole:save")
    @PostMapping(value = "/save")
    public Response<Void> save(@RequestBody UserRoleReq req) {
        userRoleService.saveUserRole(req.getUserId(), req.getRoleIds());
        return new Response<>();
    }

    @RequiresPermissions("ueboot:userRole:delete")
    @PostMapping(value = "/delete")
    public Response<Void> delete(Long[] id) {
        userRoleService.delete(id);

        // 删除用户角色日志记录
        String optUserName = (String) SecurityUtils.getSubject().getPrincipal();
        this.shiroEventListener.deleteUserRoleEvent(optUserName, id);
        return new Response<>();
    }

    @RequiresPermissions("ueboot:userRole:read")
    @GetMapping(value = "/{id}")
    public Response<UserRoleResp> get(@PathVariable Long id) {
        UserRole entity = userRoleService.get(id);
        UserRoleResp resp = new UserRoleResp();
        BeanUtils.copyProperties(entity, resp);
        return new Response<>(resp);
    }

}
