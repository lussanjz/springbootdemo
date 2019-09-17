package com.example.demo.controller;

import com.example.base.result.PageTableRequest;
import com.example.base.result.ResponseCode;
import com.example.base.result.Results;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.UserDto;
import com.example.demo.model.SysUser;
import com.example.demo.service.UserService;
import com.example.demo.util.MD5;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RequestMapping("user")
@Controller
@Slf4j

public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    @ResponseBody
    public SysUser user(@PathVariable String username) {
        log.info("UserController.user(): param (username ="+username+")");
        return userService.getUser(username);
    }

    @GetMapping("/list")
    @ResponseBody
    @ApiOperation(value = "分页获取用户信息", notes = "分页获取用户信息")//描述
    @ApiImplicitParam(name = "request", value = "分页查询实体类", required=false)
    @PreAuthorize("hasAuthority('sys:user:query')")
    public Results<SysUser> getUsers(PageTableRequest request) {
        request.countOffset();
        return userService.getAllUsersByPage(request.getOffset(),request.getLimit());
    }

    @GetMapping(value = "/add")
    @PreAuthorize("hasAuthority('sys:user:add')")
    @ApiOperation(value ="用户新增页面",notes ="跳转到新增用户信息页面")//描述)
    public String addUser(Model model) {
        model.addAttribute("sysUser",new SysUser());
        return "user/user-add";
    }

    @PostMapping("/add")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('sys:user:add')")
    @ApiOperation(value ="保存用户信息",notes ="保存新增的用户信息")//描述)
    public Results<SysUser> saveUser(UserDto userDto, Integer roleId) {
        SysUser sysUser = null;//电话验证功能
        sysUser = userService.getUserByPhone(userDto.getTelephone());
        if(sysUser != null && !(sysUser.getId().equals(userDto.getId()))){
            return Results.failure(ResponseCode.PHONE_REPEAT.getCode(),ResponseCode.PHONE_REPEAT.getMessage());

        }
        userDto.setStatus(1);
        userDto.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        //userDto.setPassword(MD5.crypt(userDto.getPassword()));
        return userService.save(userDto,roleId);
    }
    //自定义日期编辑器
    String pattern = "yyyy-MM-dd";
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(pattern), true));
    }

    @GetMapping(value = "/edit")
    @ApiOperation(value = "用户编辑页面", notes = "跳转到用户信息编辑页面")//描述
    @ApiImplicitParam(name = "user", value = "用户实体类", dataType = "SysUser")
    public String addUser(Model model, SysUser sysUser) {
        model.addAttribute(userService.getUserById(sysUser.getId()));
        return "user/user-edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @ApiOperation(value = "保存用户信息", notes = "保存编辑完的用户信息")//描述
    public Results<SysUser> updateUser(UserDto userDto, Integer roleId) {
        SysUser sysUser = null;//电话验证功能
        sysUser = userService.getUserByPhone(userDto.getTelephone());
        if(sysUser != null && !(sysUser.getId().equals(userDto.getId()))){
            return Results.failure(ResponseCode.PHONE_REPEAT.getCode(),ResponseCode.PHONE_REPEAT.getMessage());

        }
        return userService.updateUser(userDto,roleId);
    }

    @GetMapping(value = "/delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:del')")
    @ApiOperation(value = "删除用户信息", notes = "删除用户信息")//描述
    @ApiImplicitParam(name = "userDto", value = "用户实体类", required = true, dataType = "UserDto")
    public Results deleteUser(UserDto userDto) {
        int count = userService.deleteUser(userDto.getId());
        if(count > 0) {
            return Results.success();

        }else {
            return Results.failure();
        }
    }

    @GetMapping("/findUserByFuzzyUsername")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:query')")
    @ApiOperation(value = "模糊查询用户信息", notes = "模糊搜索查询用户信息")//描述
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "模糊搜索的用户名", required = true),
    })
    public Results<SysUser> findUserByFuzzyUsername(PageTableRequest request, String username) {
        log.info("UserController.findUserByFuzzyUsername(): param (request1 ="+request+" ,username = "+username+")");
        request.countOffset();
        return userService.getUserByFuzzyUsernamePage(username,request.getOffset(),request.getLimit());
    }

    @PostMapping("/changePassword")
    @ApiOperation(value = "修改密码")
    @ResponseBody
    public Results<SysUser> changePassword(String username, String oldPassword, String newPassword) {
        return userService.changePassword(username, oldPassword, newPassword);
    }

}
