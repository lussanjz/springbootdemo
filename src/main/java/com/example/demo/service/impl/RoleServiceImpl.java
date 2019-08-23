package com.example.demo.service.impl;

import com.example.base.result.Results;
import com.example.demo.dao.RoleDao;
import com.example.demo.dao.RolePermissionDao;
import com.example.demo.dto.RoleDto;
import com.example.demo.model.SysRole;
import com.example.demo.model.SysUser;
import com.example.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.Max;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

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

    @Override
    public Results<SysRole> save(RoleDto roleDto) {
        //先保存角色
        roleDao.saveRole(roleDto);
        List<Long> permissionIds = roleDto.getPermissionIds();
        //移除0，permissionId是从1开始
        permissionIds.remove(0L);
        //保存角色对应的所有权限
        if(!CollectionUtils.isEmpty(permissionIds)) {
            rolePermissionDao.save(roleDto.getId(), permissionIds);
        }
        return Results.success();
    }

    @Override
    public Results update(RoleDto roleDto) {
        List<Long> permissionIds = roleDto.getPermissionIds();
        //移除0，permissionId是从1开始
        permissionIds.remove(0L);
        //更新角色权限之前删除角色所有的权限
        rolePermissionDao.deleteRolePermission(roleDto.getId());
        //判断角色是否有赋予权限值，有就添加
        if(!CollectionUtils.isEmpty(permissionIds)) {
            rolePermissionDao.save(roleDto.getId(), permissionIds);
        }
        return roleDao.update(roleDto);
    }

}
