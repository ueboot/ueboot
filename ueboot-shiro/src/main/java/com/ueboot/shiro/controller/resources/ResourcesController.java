/*
 * Copyright (c)  2018
 * All rights reserved.
 * 2018-08-22 19:58:32
 */
package com.ueboot.shiro.controller.resources;

import com.alibaba.fastjson.JSON;
import com.ueboot.core.http.response.Response;
import com.ueboot.shiro.controller.resources.vo.*;
import com.ueboot.shiro.entity.Resources;
import com.ueboot.shiro.service.resources.ResourcesService;
import com.ueboot.shiro.shiro.ShiroEventListener;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created on 2018-08-22 19:58:32
 *
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Slf4j
@RestController
@RequestMapping(value = "/ueboot/resources")
public class ResourcesController {

    @Resource
    private ResourcesService resourcesService;

    // shiro权限记录
    @Resource
    private ShiroEventListener shiroEventListener;

    @RequiresPermissions("ueboot:resources:read")
    @PostMapping(value = "/list")
    public Response<List<ResourcesResp>> list() {
        List<Resources> all = this.resourcesService.findAll();
        List<ResourcesResp> retval = new ArrayList<>();
        all.forEach((r) -> {
            ResourcesResp resp = new ResourcesResp();
            BeanUtils.copyProperties(r, resp);
            if (r.getParent() != null) {
                resp.setParentId(r.getParent().getId());
            }
            retval.add(resp);
        });
        return new Response<>(retval);
    }


    @RequiresPermissions("ueboot:resources:read")
    @PostMapping(value = "/page")
    public Response<Page<ResourcesResp>> page(@PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.ASC)
                                                      Pageable pageable, @RequestBody(required = false) ResourcesFindReq req) {
        Page<Resources> entities = null;
        if (req.getParentId() == null) {
            entities = resourcesService.findBy(pageable);
        } else {
            entities = resourcesService.findByParentId(pageable, req.getParentId());
        }
        Page<ResourcesResp> body = entities.map(entity -> {
            ResourcesResp resp = new ResourcesResp();
            BeanUtils.copyProperties(entity, resp);
            if (entity.getParent() != null) {
                resp.setParentId(entity.getParent().getId());
            }
            return resp;
        });

        return new Response<>(body);
    }


    @RequiresPermissions("ueboot:resources:save")
    @PostMapping(value = "/save")
    public Response<Void> save(@RequestBody ResourcesReq req) {
        Resources entity = null;
        if (req.getId() == null) {
            entity = new Resources();
        } else {
            entity = resourcesService.get(req.getId());
        }
        BeanUtils.copyProperties(req, entity);
        //菜单样式配置
        Map<String, String> theme = new HashMap<>();
        if (StringUtil.isNotBlank(req.getIconName())) {
            theme.put("iconName", req.getIconName());
        }
        if (StringUtil.isNotBlank(req.getFontColor())) {
            theme.put("fontColor", req.getFontColor());
        }
        if (theme.size() > 0) {
            entity.setThemeJson(JSON.toJSONString(theme));
        } else {
            entity.setThemeJson(null);
        }
        if (req.getParentId() != 0L) {
            Resources parent = resourcesService.findById(req.getParentId());
            Assert.notNull(parent, "父节点不存在");
            entity.setParent(parent);
            entity.setParentName(parent.getName());
        } else {
            entity.setParent(null);
            entity.setParentName(null);
        }

        resourcesService.save(entity);

        // 保存/修改资源日志记录
        String optUserName = (String) SecurityUtils.getSubject().getPrincipal();
        this.shiroEventListener.saveResourceEvent(optUserName, req.getName());
        return new Response<>();
    }

    @RequiresPermissions("ueboot:resources:delete")
    @PostMapping(value = "/delete")
    public Response<Void> delete(Long[] id) {
        resourcesService.deleteResource(id);
        return new Response<>();
    }

    @RequiresPermissions("ueboot:resources:read")
    @GetMapping(value = "/{id}")
    public Response<ResourcesResp> get(@PathVariable Long id) {
        Resources entity = resourcesService.get(id);
        ResourcesResp resp = new ResourcesResp();
        BeanUtils.copyProperties(entity, resp);
        return new Response<>(resp);
    }

}
