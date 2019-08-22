package com.example.demo.service;

import com.alibaba.fastjson.JSONArray;
import com.example.base.result.Results;

public interface PermissionService {
     Results<JSONArray> listAllPermission();
}
