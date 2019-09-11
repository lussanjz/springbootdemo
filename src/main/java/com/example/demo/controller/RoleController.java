package com.example.demo.controller;

import com.example.base.result.PageTableRequest;
import com.example.base.result.ResponseCode;
import com.example.base.result.Results;
import com.example.demo.dto.RoleDto;
import com.example.demo.dto.UserDto;
import com.example.demo.model.SysRole;
import com.example.demo.model.SysUser;
import com.example.demo.service.RoleService;
import com.example.demo.util.MD5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/add")
    public String addRole(Model model) {
        model.addAttribute("sysRole",new SysRole());
        return "role/role-add";
    }

    @PostMapping(value = "/add")
    @ResponseBody
    public Results saveRole(@RequestBody RoleDto roleDto) {
        return roleService.save(roleDto);
    }

    @GetMapping(value = "/edit")
    public String editRole(Model model, SysRole role) {
        model.addAttribute("sysRole",roleService.getRoleById(role.getId()));
        return "role/role-edit";
    }

    @PostMapping(value = "/edit")
    @ResponseBody
    public Results updateRole(@RequestBody RoleDto roleDto) {
        return roleService.update(roleDto);
    }

    @GetMapping(value = "/delete")
    @ResponseBody
    public Results<SysRole> deleteRole(RoleDto roleDto) {
        return roleService.delete(roleDto.getId());

    }
}
