package com.example.demo.service;

import com.example.base.result.Results;
import com.example.demo.model.SysRole;

public interface RoleService {
    Results<SysRole> getAllRoles();

    Results<SysRole> getAllRolesByPage(Integer offset, Integer limit);

    Results<SysRole> getRoleByFuzzyRoleNamePage(String roleName, Integer offset, Integer limit);
}
