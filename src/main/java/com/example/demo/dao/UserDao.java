package com.example.demo.dao;

import com.example.demo.model.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserDao {
    @Select("select * from sys_user t where t.username = #{username}")
    SysUser getUser(String username);

    @Select("select * from sys_user t order by t.id limit #{startPosition} , #{limit}")
    List<SysUser> getAllUsersByPage(@Param("startPosition")Integer startPosition, @Param("limit")Integer limit);

    @Select("select count(*) from sys_user t")
    Long countAllUsers();


}
