package com.example.demo.service.impl;

import com.example.demo.dao.PermissionDao;
import com.example.demo.dto.LoginUser;
import com.example.demo.model.SysUser;
import com.example.demo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailslServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userService.getUser(username);
        if(sysUser == null){
            throw new AuthenticationCredentialsNotFoundException("用户名不存在");
        }else if(sysUser.getStatus() == SysUser.Status.LOCKED){
            throw new LockedException("用户被锁定，请联系管理员");
        }
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(sysUser,loginUser);
        loginUser.setPermissions(permissionDao.listByUserId(sysUser.getId()));
        return loginUser;
    }
}
