package com.app.mdc.service.system;


import com.baomidou.mybatisplus.service.IService;
import com.app.mdc.model.system.Role;
import com.app.mdc.model.system.User;
import com.app.mdc.utils.viewbean.ResponseResult;

public interface RoleService extends IService<Role>{
	
	/**
	 * 新增
     * @param status	状态
	 * @param name	名字
	 * @param code	角色编码
	 * @param remark	备注
	 * @param rank	排序
	 * @param menuIds	菜单id的集合
     */
	ResponseResult addRole(String status,String name,String code,String remark,Integer rank,String menuIds);
	
	/**
	 * 查找角色列表
	 * @param name 角色名称
	 * @param code	角色编码
	 * @return
	 */
	ResponseResult findRoles(String name,String code,String status);

	/**
	 * 删除角色
	 * @param id 角色id
	 * @return
	 */
	ResponseResult deleteRole(String id);
	
	/**
	 * 获取某一个角色信息
	 * @param id 角色id
	 * @return
	 */
	ResponseResult getOneRole(String id);
	
	
	/**
	 *	更新角色 
	 * @param id 
	 * @param status
	 * @param name
	 * @param code
	 * @param remark
	 * @param rank
	 * @param menuIds
	 * @return
	 */
	ResponseResult updateRole(String id,String status,String name,String code,String remark,Integer rank,String menuIds);
	
	/**
	 * 获取当前登录人的一级菜单
	 * @return
	 */
	ResponseResult getFirstMenu(User user);
}
