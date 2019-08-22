package com.example.demo.dao;

import com.example.demo.model.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionDao {
    @Select("select * from sys_permission t")
    List<SysPermission> findAll();
}
