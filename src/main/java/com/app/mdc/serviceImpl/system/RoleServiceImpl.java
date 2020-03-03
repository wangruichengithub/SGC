package com.app.mdc.serviceImpl.system;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.app.mdc.mapper.system.RoleMapper;
import com.app.mdc.mapper.system.RoleMenuMapper;
import com.app.mdc.mapper.system.RoleUserMapper;
import com.app.mdc.model.system.Menu;
import com.app.mdc.model.system.Role;
import com.app.mdc.model.system.RoleMenu;
import com.app.mdc.model.system.User;
import com.app.mdc.service.system.RoleService;
import com.app.mdc.utils.jdbc.SqlUtils;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

	private final RoleMenuMapper roleMenuMapper;
	private final RoleUserMapper roleUserMapper;

	@Autowired
	public RoleServiceImpl(RoleMenuMapper roleMenuMapper,RoleUserMapper roleUserMapper) {
		this.roleMenuMapper=roleMenuMapper;
		this.roleUserMapper=roleUserMapper;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseResult addRole(String status, String name, String code, String remark, Integer rank, String menuIds) {
		Role role=new Role();
		role.setStatus(status);
		role.setName(name);
		role.setCode(code);
		role.setRemark(remark);
		role.setDeleted(0);
		role.setCreatetime(new Date());
		role.setUpdatetime(new Date());

		int rowCount = this.baseMapper.insert(role);

        //获取roleid加到中间表里面
		if(com.baomidou.mybatisplus.toolkit.StringUtils.isNotEmpty(menuIds)) {
			String role_id=role.getId();
			String[] arr = menuIds.split(",");
	        for (String string : arr) {
	        	RoleMenu rolemenu=new RoleMenu();
	        	rolemenu.setRoleId(role_id);
	        	rolemenu.setMenuId(string);
	        	roleMenuMapper.insert(rolemenu);
			}
		}
        return rowCount == 1 ? ResponseResult.success() : ResponseResult.fail();
	}

	@Override
	public ResponseResult findRoles(String name, String code,String status) {
		EntityWrapper<Role> entityWrapper = new EntityWrapper<>();
		entityWrapper.eq("deleted", 0);

		if(!StringUtils.isEmpty(name)) {
			entityWrapper.like("name", name);
		}
		if(!StringUtils.isEmpty(code)) {
			entityWrapper.like("code",code);
		}
		if(!StringUtils.isEmpty(status)) {
			entityWrapper.eq("status",status);
		}
        entityWrapper.orderDesc(SqlUtils.orderBy("updatetime"));
		List<Role> list=this.baseMapper.selectList(entityWrapper);
		return ResponseResult.success().add(list);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseResult deleteRole(String id) {
		Map<String,Object> map=new HashMap<>();
		//id是111,222,333格式
		if(com.baomidou.mybatisplus.toolkit.StringUtils.isNotEmpty(id)){
			String ids[]=id.split(",");
			for (String string : ids){
				Role role=new Role();
				role.setId(string);
				role.setDeleted(1);
				role.setUpdatetime(new Date());
				this.baseMapper.updateById(role);
				//同时把角色和用户的中间表删除
				map.put("role_id",string);
				roleUserMapper.deleteByMap(map);
				//把角色和菜单中间表删除
				roleMenuMapper.deleteByMap(map);
			}

		}
        return ResponseResult.success();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseResult updateRole(String id, String status, String name, String code, String remark, Integer rank,
			String menuIds) {
		Role role=new Role();
		role.setId(id);
		role.setStatus(status);
		role.setName(name);
		role.setCode(code);
		role.setRemark(remark);
		role.setUpdatetime(new Date());

		//删除原role对应的菜单，重新添加
		Map<String, Object> map= new HashMap<>();
		map.put("role_id", id);
		roleMenuMapper.deleteByMap(map);

		if(menuIds.length()>0) {
			//获取roleid加到中间表里面
			String[] arr = menuIds.split(",");
	        for (String string : arr) {
	        	RoleMenu rolemenu=new RoleMenu();
	        	rolemenu.setRoleId(id);
	        	rolemenu.setMenuId(string);
	        	roleMenuMapper.insert(rolemenu);
			}
		}

        int rowCount = this.baseMapper.updateById(role);
        return rowCount == 1 ? ResponseResult.success() : ResponseResult.fail();
	}

	@Override
	public ResponseResult getOneRole(String id) {
		Role role=this.baseMapper.selectById(id);
		//根据roleid获取菜单list
		EntityWrapper<RoleMenu> entity=new EntityWrapper<>();
		entity.eq("role_id", role.getId());
		List<RoleMenu> menus=roleMenuMapper.selectList(entity);
		//获取菜单ids
		StringBuilder menuIds= new StringBuilder();
		for (int i = 0; i < menus.size(); i++) {
			if(i+1==menus.size()) {
				menuIds.append(menus.get(i).getMenuId());
			}else {
				menuIds.append(menus.get(i).getMenuId()).append(",");
			}
		}
		//重新组合拼接返回给前端
		JSONObject json=new JSONObject();
		json.put("menuIds", menuIds.toString());
		json.put("id", role.getId());
		json.put("status", role.getStatus());
		json.put("name", role.getName());
		json.put("code", role.getCode());
		json.put("remark", role.getRemark());

		return ResponseResult.success().add(json);
	}

	@Override
	public ResponseResult getFirstMenu(User user) {
		List<Menu> menus=roleMenuMapper.getFirstMenus(user.getId());
		return ResponseResult.success().add(menus);
	}

}
