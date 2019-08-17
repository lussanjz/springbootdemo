package com.example.demo.service.impl;

import com.example.demo.dao.UserDao;
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

    @Override
    public SysUser getUser(String username) {return userDao.getUser(username);}
}
