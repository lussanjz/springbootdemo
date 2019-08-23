package com.example.demo.service;

import com.alibaba.fastjson.JSONArray;
import com.example.base.result.Results;
import com.example.demo.model.SysPermission;

public interface PermissionService {
     Results<JSONArray> listAllPermission();

    Results<SysPermission> listByRoleId(Integer intValue);
}
