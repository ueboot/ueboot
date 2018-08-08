/*
* Copyright (c)  2018
* All rights reserved.
* 2018-08-08 11:53:52
*/
package com.ueboot.shiro.controller.organization;

import com.ueboot.core.http.response.Response;
import com.ueboot.shiro.controller.organization.vo.*;
import com.ueboot.shiro.entity.Organization;
import com.ueboot.shiro.service.organization.OrganizationService;
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
 * Created on 2018-08-08 11:53:52
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Slf4j
@RestController
@RequestMapping(value = "/platform/organization")
public class OrganizationController {

    @Resource
    private OrganizationService organizationService;


    @RequiresPermissions("organization:read")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Response<Page<OrganizationResp>> page(@PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC)
                                                     Pageable pageable, @RequestBody(required = false) OrganizationFindReq req){
        Page<Organization> entities = organizationService.findBy(pageable);
        Page<OrganizationResp> body = entities.map(entity -> {
            OrganizationResp resp = new OrganizationResp();
            BeanUtils.copyProperties(entity, resp);
            return resp;
        });

        return new Response<>(body);
    }


    @RequiresPermissions("organization:save")
    @PostMapping(value = "/save")
    public Response<Void> save(@RequestBody OrganizationReq req) {
        Organization entity = null;
        if (req.getId() == null) {
            entity = new Organization();
        } else {
            entity = organizationService.get(req.getId());
        }
        BeanUtils.copyProperties(req, entity);
        organizationService.save(entity);
        return new Response<>();
    }

    @RequiresPermissions("organization:delete")
    @PostMapping(value = "/delete")
    public Response<Void> delete(Long[] id) {
        organizationService.delete(id);
        return new Response<>();
    }

    //@RequiresPermissions("organization:read")
    @GetMapping(value = "/{id}")
    public Response<OrganizationResp> get(@PathVariable Long id) {
        Organization entity = organizationService.get(id);
        OrganizationResp resp = new OrganizationResp();
        BeanUtils.copyProperties(entity, resp);
        return new Response<>(resp);
    }

}
