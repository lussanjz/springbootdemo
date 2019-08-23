package com.example.demo.dto;

import com.example.demo.model.SysRole;

import java.util.List;

public class RoleDto extends SysRole {
    private List<Long> permissionIds;
    public List<Long> getPermissionIds() {return permissionIds;}
    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }

}
