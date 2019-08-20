package com.example.demo.controller;

import com.example.base.result.PageTableRequest;
import com.example.base.result.Results;
import com.example.demo.model.SysRole;
import com.example.demo.model.SysUser;
import com.example.demo.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("role")
@Controller
@Slf4j
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    @ResponseBody
    public Results<SysRole> getAll(){
        log.info("RoleController.getAll()");
        return roleService.getAllRoles();
    }

    @GetMapping("/list")
    @ResponseBody
    public Results list(PageTableRequest request) {
        log.info("RoleController.list(): param( request= "+request+")");
        request.countOffset();
        return roleService.getAllRolesByPage(request.getOffset(),request.getLimit());
    }

    @GetMapping("/findRoleByFuzzyRoleName")
    @ResponseBody
    public Results<SysRole> findRoleByFuzzyRoleName(PageTableRequest request, String roleName) {
        log.info("RoleController.findRoleByFuzzyRoleName(): param (request ="+request+" ,roleName = "+roleName+")");
        request.countOffset();
        return roleService.getRoleByFuzzyRoleNamePage(roleName,request.getOffset(),request.getLimit());
    }
}
