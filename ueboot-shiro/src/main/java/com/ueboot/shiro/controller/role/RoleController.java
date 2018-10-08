/*
 * Copyright (c)  2018
 * All rights reserved.
 * 2018-08-21 09:40:34
 */
package com.ueboot.shiro.controller.role;

import com.ueboot.core.exception.BusinessException;
import com.ueboot.core.http.response.Response;
import com.ueboot.shiro.controller.role.vo.*;
import com.ueboot.shiro.entity.Role;
import com.ueboot.shiro.service.role.RoleService;
import com.ueboot.shiro.shiro.ShiroEventListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * Created on 2018-08-21 09:40:34
 *
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Slf4j
@RestController
@RequestMapping(value = "/ueboot/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    // shiro权限记录
    @Resource
    private ShiroEventListener shiroEventListener;

    @RequiresPermissions("ueboot:role:read")
    @PostMapping(value = "/list")
    public Response<List<RoleResp>> list() {
        List<Role> all = this.roleService.findAll();
        List<RoleResp> retval = new ArrayList<>();
        all.forEach((r) -> {
            RoleResp resp = new RoleResp();
            BeanUtils.copyProperties(r, resp);
            retval.add(resp);
        });
        return new Response<>(retval);
    }

    @RequiresPermissions("ueboot:role:read")
    @PostMapping(value = "/page")
    public Response<Page<RoleResp>> page(@PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.ASC)
                                                 Pageable pageable, @RequestBody(required = false) RoleFindReq req) {

        Page<Role> entities = roleService.findByName(pageable, req != null ? req.getName() : "");

        Page<RoleResp> body = entities.map(entity -> {
            RoleResp resp = new RoleResp();
            BeanUtils.copyProperties(entity, resp);
            return resp;
        });

        return new Response<>(body);
    }


    @RequiresPermissions("ueboot:role:save")
    @PostMapping(value = "/save")
    public Response<Void> save(@RequestBody RoleReq req) {
        Role entity = null;
        if (req.getId() == null) {
            entity = new Role();
            Role role = roleService.findByName(req.getName());
            if (role != null) {
                throw new BusinessException("当前角色名称已经存在，不能重复添加!");
            }
        } else {
            entity = roleService.get(req.getId());
        }
        BeanUtils.copyProperties(req, entity);
        roleService.save(entity);

        // 保存/修改 角色日志记录
        String optUserName = (String) SecurityUtils.getSubject().getPrincipal();
        this.shiroEventListener.saveRoleEvent(optUserName, req.getName());
        return new Response<>();
    }

    @RequiresPermissions("ueboot:role:delete")
    @PostMapping(value = "/delete")
    public Response<String> delete(Long[] id) {

        if(id==null||id.length==0||id.length!=1){

            throw new BusinessException("请选择需要删除的角色!");
        }
        Long roleId=id[0];
        //统计是否授权有用户
        Long  statisticNum=roleService.statisticUserByRoleId(roleId);
        if(statisticNum>0){
            throw new BusinessException("该角色已被分配，需要解除分配才可以删除！");
        }
        roleService.delete(id);
        // 删除 角色日志记录
        String optUserName = (String) SecurityUtils.getSubject().getPrincipal();
        this.shiroEventListener.deleteRoleEvent(optUserName, id);
        return new Response<>();
    }


    @RequiresPermissions("ueboot:role:read")
    @GetMapping(value = "/{id}")
    public Response<RoleResp> get(@PathVariable Long id) {
        Role entity = roleService.get(id);
        RoleResp resp = new RoleResp();
        BeanUtils.copyProperties(entity, resp);
        return new Response<>(resp);
    }

}
