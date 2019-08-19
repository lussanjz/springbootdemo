package com.example.demo.dao;

import com.example.demo.model.SysRoleUser;
import com.example.demo.model.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleUserDao {
    @Insert("insert into sys_role_user(userId, roleId) values(#{userId}, #{roleId})")
    void save(SysRoleUser sysRoleUser);


    @Select("select * from sys_role_user t where t.userid = #{userid}")
    SysRoleUser getSysRoleUserByUserId(Integer userId);
}
