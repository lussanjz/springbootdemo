package com.example.demo.controller;


import com.example.base.result.Results;
import com.example.demo.dto.UserDto;
import com.example.demo.model.SysUser;
import com.example.demo.service.RoleUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("roleuser")
@Slf4j
public class RoleUserController {
    @Autowired
    private RoleUserService roleUserService;
    @PostMapping("/getRoleUserById")
    public Results getRoleUserByUserId(Integer userId) {
        log.info("RoleUserController.getRoleUserByUserId: param=" + userId);
        return roleUserService.getSysRoleUserByUserId(userId);

    }
}
