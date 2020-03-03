package com.app.mdc.mapper.system;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.app.mdc.model.system.Role;

import java.util.List;

@Component
public interface RoleMapper extends BaseMapper<Role>{

    List<Role> findRolesByUserId(@Param(value = "userId") String id);
}
