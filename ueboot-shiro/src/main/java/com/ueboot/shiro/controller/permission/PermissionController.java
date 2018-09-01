/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-08 14:05:30
*/
package com.ueboot.shiro.controller.permission;

import com.ueboot.core.http.response.Response;
import com.ueboot.shiro.controller.permission.vo.PermissionFindReq;
import com.ueboot.shiro.controller.permission.vo.PermissionReq;
import com.ueboot.shiro.controller.permission.vo.PermissionResp;
import com.ueboot.shiro.entity.Permission;
import com.ueboot.shiro.service.permission.PermissionService;
import lombok.extern.slf4j.Slf4j;
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
 * Created on 2018-08-08 14:05:30
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Slf4j
@RestController
@RequestMapping(value = "/ueboot/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;


    @RequiresPermissions("ueboot:permission:read")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Response<Page<PermissionResp>> page(@PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC)
                                                     Pageable pageable, @RequestBody(required = false) PermissionFindReq req){
        Page<Permission> entities = permissionService.findBy(pageable);
        Page<PermissionResp> body = entities.map(entity -> {
            PermissionResp resp = new PermissionResp();
            BeanUtils.copyProperties(entity, resp);
            return resp;
        });

        return new Response<>(body);
    }

    /**
     * 根据角色获取角色所属的权限资源
     * @param req
     * @return
     */
    @RequiresPermissions("ueboot:permission:read")
    @PostMapping(value = "/findByRoleId")
    public Response<List<PermissionResp>> findByUserId(@RequestBody PermissionReq req) {
        List<Permission> permissions = permissionService.findByRoleId(req.getRoleId());
        List<PermissionResp> result = new ArrayList<>();
        permissions.forEach((u)->{
            PermissionResp resp = new PermissionResp();
            BeanUtils.copyProperties(u,resp);
            resp.setRoleId(u.getRole().getId());
            resp.setResourceId(u.getResource().getId());
            result.add(resp);
        });
        return new Response<>(result);
    }

    @RequiresPermissions("ueboot:permission:save")
    @PostMapping(value = "/save")
    public Response<Void> save(@RequestBody PermissionReq req) {
        permissionService.saveRolePermission(req.getRoleId(),req.getResourceIds());
        return new Response<>();
    }

    @RequiresPermissions("ueboot:permission:delete")
    @PostMapping(value = "/delete")
    public Response<Void> delete(Long[] id) {
        permissionService.delete(id);
        return new Response<>();
    }

    @RequiresPermissions("ueboot:permission:read")
    @GetMapping(value = "/{id}")
    public Response<PermissionResp> get(@PathVariable Long id) {
        Permission entity = permissionService.get(id);
        PermissionResp resp = new PermissionResp();
        BeanUtils.copyProperties(entity, resp);
        return new Response<>(resp);
    }

}
