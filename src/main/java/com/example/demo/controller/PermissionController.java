package com.example.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.example.base.result.Results;
import com.example.demo.dto.RoleDto;
import com.example.demo.model.SysPermission;
import com.example.demo.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("permission")
@Slf4j
public class PermissionController {
    @Autowired
    private PermissionService permissionService;


    @RequestMapping(value = "/listAllPermission", method = RequestMethod.GET)
    @ResponseBody
    public Results<JSONArray> listAllPermission() {
        return permissionService.listAllPermission();
    }

    public Results<SysPermission> listAllPermissionByRoleId(RoleDto roleDto) {
        log.info(getClass().getName() +":param =" +roleDto);
        return permissionService.listByRoleId(roleDto.getId().intValue());
    }

}
