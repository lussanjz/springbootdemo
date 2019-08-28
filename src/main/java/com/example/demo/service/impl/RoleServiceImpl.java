package com.example.demo.service.impl;

import com.example.base.result.ResponseCode;
import com.example.base.result.Results;
import com.example.demo.dao.RoleDao;
import com.example.demo.dao.RolePermissionDao;
import com.example.demo.dao.RoleUserDao;
import com.example.demo.dto.RoleDto;
import com.example.demo.model.SysRole;
import com.example.demo.model.SysRoleUser;
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

    @Autowired
    private RoleUserDao roleUserDao;

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
    public SysRole getRoleById(Integer id) {
        return roleDao.getById(id);
    }

    @Override
    public Results update(RoleDto roleDto) {
        List<Long> permissionIds = roleDto.getPermissionIds();
        permissionIds.remove(0L);
        //1、更新角色权限之前要删除该角色之前的所有权限
        rolePermissionDao.deleteRolePermission(roleDto.getId());

        //2、判断该角色是否有赋予权限值，有就添加"
        if (!CollectionUtils.isEmpty(permissionIds)) {
            rolePermissionDao.save(roleDto.getId(), permissionIds);
        }

        //3、更新角色表
        int countData = roleDao.update(roleDto);

        if(countData > 0){
            return Results.success();
        }else{
            return Results.failure();
        }
    }

    @Override
    public Results delete(Integer id) {
        List<SysRoleUser> datas = roleUserDao.listAllSysRoleUserByRoleId(id);
        if(datas.size()<= 0) {
            roleDao.delete(id);
            return Results.success();
        }
        return Results.failure(ResponseCode.USERNAME_REPEAT.USER_ROLE_NO_CLEAR.getCode(), ResponseCode.USERNAME_REPEAT.USER_ROLE_NO_CLEAR.getMessage());
    }

}
