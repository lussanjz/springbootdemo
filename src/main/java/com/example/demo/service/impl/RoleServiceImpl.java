package com.example.demo.service.impl;

import com.example.base.result.Results;
import com.example.demo.dao.RoleDao;
import com.example.demo.model.SysRole;
import com.example.demo.model.SysUser;
import com.example.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Results<SysRole> getAllRoles() {
        return Results.success(50, roleDao.getAllRoles());
    }

    @Override
    public Results<SysRole> getAllRolesByPage(Integer offset, Integer limit) {
        return Results.success(roleDao.countAllRoles().intValue(), roleDao.getAllRolesByPage(offset, limit));
    }

    @Override
    public Results<SysRole> getRoleByFuzzyRoleNamePage(String roleName, Integer startPosition, Integer limit) {
        return Results.success(roleDao.countRoleByFuzzyRoleName(roleName).intValue(), roleDao.getRoleByFuzzyRoleNamePage(roleName, startPosition, limit));
    }

}
