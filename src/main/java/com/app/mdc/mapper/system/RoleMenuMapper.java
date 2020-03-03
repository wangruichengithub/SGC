package com.app.mdc.mapper.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.app.mdc.model.system.Menu;
import com.app.mdc.model.system.RoleMenu;

@Component
public interface RoleMenuMapper extends BaseMapper<RoleMenu>{

	List<Menu> getFirstMenus(@Param(value = "userId") String userId);
	
	List<Menu> getChildMenus(Map<String, Object> map);
}
