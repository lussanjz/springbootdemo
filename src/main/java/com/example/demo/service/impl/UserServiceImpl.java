package com.example.demo.service.impl;

import com.example.base.result.Results;
import com.example.demo.dao.RoleUserDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.UserDto;
import com.example.demo.model.SysRoleUser;
import com.example.demo.model.SysUser;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Override
    public Results<SysUser> updateUser(UserDto userDto, Integer roleId) {
        if(roleId != null) {
            //sysuser update
            userDao.updateUser(userDto);
            //sysroleuser update,save
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setUserId(userDto.getId().intValue());
            sysRoleUser.setRoleId(roleId);
            if(roleUserDao.getSysRoleUserByUserId(userDto.getId().intValue()) != null) {
                roleUserDao.updateSysRoleUser(sysRoleUser);
            }else {
                roleUserDao.save(sysRoleUser);
            }
            return Results.success();
        }else {
            return Results.failure();
        }
    }

    @Override
    public int deleteUser(Long id) {
        roleUserDao.deleteRoleUserByUserId(id.intValue());
        return userDao.deleteUser(id.intValue());
         }

    @Override
    public Results<SysUser> getUserByFuzzyUsernamePage(String username, Integer startPosition, Integer limit) {
        return Results.success(userDao.getUserByFuzzyUsername(username).intValue(), userDao.getUserByFuzzyUsernamePage(username, startPosition, limit));
    }

    @Override
    public Results<SysUser> changePassword(String username, String oldPassword, String newPassword) {
        SysUser u = userDao.getUser(username);
//        if (u == null) {
//            return Results.failure(1,"用户不存在");
//        }
//        if (!new BCryptPasswordEncoder().encode(oldPassword).equals(u.getPassword())) {
//            return Results.failure(1,"旧密码错误");
//        }
        userDao.changePassword(u.getId(), new BCryptPasswordEncoder().encode(newPassword));
        return Results.success();
    }
}