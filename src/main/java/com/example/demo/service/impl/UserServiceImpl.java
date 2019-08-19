package com.example.demo.service.impl;

import com.example.base.result.Results;
import com.example.demo.dao.RoleUserDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.UserDto;
import com.example.demo.model.SysRoleUser;
import com.example.demo.model.SysUser;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    public UserDao userDao;

    @Autowired
    public RoleUserDao roleUserDao;

    @Override
    public SysUser getUser(String username) {return userDao.getUser(username);}

    @Override
    public Results<SysUser> getAllUsersByPage(Integer offset, Integer limit) {
        return Results.success(userDao.countAllUsers().intValue(), userDao.getAllUsersByPage(offset, limit));
    }

    @Override
    public Results save(SysUser user, Integer roleId) {
        if(roleId != null){
            userDao.save(user);
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setRoleId(roleId);
            sysRoleUser.setUserId(user.getId().intValue());
            roleUserDao.save(sysRoleUser);
            return Results.success();
        }
        return Results.failure();
    }

    @Override
    public SysUser getUserByPhone(String telephone) {
        return userDao.getAllUsersByPhone(telephone);
    }

    @Override
    public SysUser getUserById(Long id) {
        return userDao.getUserById(id);
    }
}
