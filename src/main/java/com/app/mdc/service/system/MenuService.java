package com.app.mdc.service.system;

import com.baomidou.mybatisplus.service.IService;
import com.app.mdc.model.system.Menu;
import com.app.mdc.model.system.User;
import com.app.mdc.utils.viewbean.ResponseResult;

public interface MenuService extends IService<Menu>{
	
	
	ResponseResult getMenusByPid(String parentId,User user);
	
	/**
	 * 新增
	 * @param status 状态
	 * @param name	名字
	 * @param icon	图标
	 * @param url	链接
	 * @param level	级别
	 * @param parentId	父级id
	 * @param rank	排序
	 * @return
	 */
	ResponseResult addMenu(String status,String name,String icon,String url,Integer level,String parentId,Integer rank);
	
	/**
	 * 获取菜单list
	 * @param name 菜单名称
	 * @return
	 */
	ResponseResult menuList(String name);
	
	/**
	 * 删除菜单
	 * @param id 菜单id
	 * @return
	 */
	ResponseResult delMenu(String id);
	
	/**
	 * 获取一条菜单数据
	 * @param id 菜单id
	 * @return
	 */
	ResponseResult getOneMenu(String id);
	
	/**
	 * 修改
	 * @param status 状态
	 * @param name	名字
	 * @param icon	图标
	 * @param url	链接
	 * @param level	级别
	 * @param parentId	父级id
	 * @param rank	排序
	 * @return
	 */
	ResponseResult updateMenu(String id,String status,String name,String icon,String url,Integer level,String parentId,Integer rank);
	
	
}
