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

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_user(username, password, nickname, headImgUrl, phone, telephone, email, birthday, sex, status, createTime, updateTime) values(#{username}, #{password}, #{nickname}, #{headImgUrl}, #{phone}, #{telephone}, #{email}, #{birthday}, #{sex}, #{status}, now(), now())")
    int save(SysUser user);


}
