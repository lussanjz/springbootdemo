package com.example.demo.service;

import com.example.base.result.Results;
import com.example.demo.model.SysRole;

public interface RoleService {
    Results<SysRole> getAllRoles();
}
