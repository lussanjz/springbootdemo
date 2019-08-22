package com.example.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.example.base.result.Results;
import com.example.demo.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("permission")
@Slf4j
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @RequestMapping(value = "/listAllPermission", method = RequestMethod.GET)
    public Results<JSONArray> listAllPermission() {
        return  permissionService.listAllPermission;
    }

}
