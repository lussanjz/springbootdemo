package com.example.demo.service.impl;

import com.example.base.result.Results;
import com.example.demo.dao.RoleUserDao;
import com.example.demo.model.SysRoleUser;
import com.example.demo.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleUserServiceImpl implements RoleUserService {
    @Autowired
    private RoleUserDao roleUserDao;
    @Override
    public Results getSysRoleUserByUserId(Integer userId) {
        SysRoleUser sysRoleUser = roleUserDao.getSysRoleUserByUserId(userId);
        if(sysRoleUser != null){
            return Results.success(sysRoleUser);

        }
        return Results.success();
    }
}
