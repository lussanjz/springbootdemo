package com.example.demo.service;

import com.example.base.result.Results;
import org.springframework.stereotype.Service;


public interface RoleUserService {
    Results getSysRoleUserByUserId(Integer userId);
}
