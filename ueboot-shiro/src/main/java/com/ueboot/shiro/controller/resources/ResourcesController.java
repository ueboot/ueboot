/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-08 14:06:03
*/
package com.ueboot.shiro.controller.resources;

import com.ueboot.core.http.response.Response;
import com.ueboot.shiro.controller.resources.vo.*;
import com.ueboot.shiro.entity.Resources;
import com.ueboot.shiro.service.resources.ResourcesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;


/**
 * Created on 2018-08-08 14:06:03
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Slf4j
@RestController
@RequestMapping(value = "/platform/resources")
public class ResourcesController {

    @Resource
    private ResourcesService resourcesService;


    @RequiresPermissions("resources:read")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Response<Page<ResourcesResp>> page(@PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC)
                                                     Pageable pageable, @RequestBody(required = false) ResourcesFindReq req){
        Page<Resources> entities = resourcesService.findBy(pageable);
        Page<ResourcesResp> body = entities.map(entity -> {
            ResourcesResp resp = new ResourcesResp();
            BeanUtils.copyProperties(entity, resp);
            return resp;
        });

        return new Response<>(body);
    }


    @RequiresPermissions("resources:save")
    @PostMapping(value = "/save")
    public Response<Void> save(@RequestBody ResourcesReq req) {
        Resources entity = null;
        if (req.getId() == null) {
            entity = new Resources();
        } else {
            entity = resourcesService.get(req.getId());
        }
        BeanUtils.copyProperties(req, entity);
        resourcesService.save(entity);
        return new Response<>();
    }

    @RequiresPermissions("resources:delete")
    @PostMapping(value = "/delete")
    public Response<Void> delete(Long[] id) {
        resourcesService.delete(id);
        return new Response<>();
    }

    @RequiresPermissions("resources:read")
    @GetMapping(value = "/{id}")
    public Response<ResourcesResp> get(@PathVariable Long id) {
        Resources entity = resourcesService.get(id);
        ResourcesResp resp = new ResourcesResp();
        BeanUtils.copyProperties(entity, resp);
        return new Response<>(resp);
    }

}
