package com.example.demo.dao;

import com.example.demo.model.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleDao {
    @Select("select * from sys_role t")
    List<SysRole> getAllRoles();

    @Select("select count(*) from sys_role t")
    Long countAllRoles();

    @Select("select * from sys_role t limit #{startPosition} , #{limit}")
    List<SysRole> getAllRolesByPage(@Param("startPosition")Integer startPosition, @Param("limit")Integer limit);

    @Select("select * from sys_role t where t.name like '%${roleName}%' limit #{startPosition} , #{limit}")
    List<SysRole> getRoleByFuzzyRoleNamePage(@Param("roleName") String roleName, @Param("startPosition") Integer startPosition, @Param("limit") Integer limit);

    @Select("select count(*) from sys_role t where t.name like '%${roleName}%'")
    Long countRoleByFuzzyRoleName(@Param("roleName") String roleName);
}
