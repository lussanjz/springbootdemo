package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.example.base.result.Results;
import com.example.demo.dao.PermissionDao;
import com.example.demo.model.SysPermission;
import com.example.demo.service.PermissionService;
import com.example.demo.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionDao permissionDao;

    @Override
    public Results<JSONArray> listAllPermission() {
       log.info(getClass().getName()+ ".listAllPermission()");
        List datas = permissionDao.findAll();
        JSONArray array = new JSONArray();
        log.info(getClass().getName()+".setPermissiontree(?,?,?)");
        TreeUtils.setPermissionsTree(0,datas,array);
        return Results.success(array);
    }

    @Override
    public Results<SysPermission> listByRoleId(Integer roleId) {
        List<SysPermission> datas = permissionDao.listByRoleId(roleId);
        return Results.success(0,datas);
    }
}
