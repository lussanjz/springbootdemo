package com.example.demo.dao;

import com.example.demo.model.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleDao {
    @Select("select * from sys_role t")
    List<SysRole> getAllRoles();
}
