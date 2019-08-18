package com.example.demo.service;

import com.example.base.result.Results;
import com.example.demo.model.SysUser;

public interface UserService {
    SysUser getUser(String username);

    Results<SysUser> getAllUsersByPage(Integer offset, Integer limit);
}
