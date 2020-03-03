package com.app.mdc.controller.system;

import com.app.mdc.annotation.anno.SystemLogAnno;
import com.app.mdc.controller.BaseController;
import com.app.mdc.model.system.User;
import com.app.mdc.service.system.RoleService;
import com.app.mdc.utils.viewbean.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {
	
	private RoleService roleService;
	
	@Autowired
	public RoleController(RoleService roleService) {
		this.roleService=roleService;
	}
	/**
	 * 获取第一级的菜单
	 * @param httpSession
	 * @return 第一级菜单的list
	 */
	@PostMapping("/getFirstMenu")
	@ResponseBody
	public ResponseResult getFirstMenu(HttpSession httpSession) {
		User user = currentUser(httpSession);
		return roleService.getFirstMenu(user);
	}


	/**
	 * 添加角色，同时添加角色菜单中间表	
	 * @param status	状态
	 * @param name	名字
	 * @param code	角色编码
	 * @param remark	备注
	 * @param rank	排序
	 * @param menuIds	菜单id的集合
	 * @return 返回的结果，0正确ERR500错误
	 */
	@PostMapping("/addRole")
	@SystemLogAnno(module = "角色管理", operation = "新增角色")
	@ResponseBody
	public ResponseResult addRole(String status,String name,String code,String remark,Integer rank,String menuIds) {
		return roleService.addRole(status, name, code, remark, rank,menuIds);
	}
	
	/**
	 * 查找角色list
	 * @param name	角色名称
	 * @param code	角色编码
	 * @return 获取所有的list
	 */
	@PostMapping("/findRoles")
	@ResponseBody
	public ResponseResult findRoles(String name,String code,String status) {
		return roleService.findRoles(name, code,status);
	}
	
	/**
	 * 删除角色
	 * @param id 角色id
	 * @return 返回的结果，0正确ERR500错误
	 */
	@PostMapping("/deleteRole")
	@SystemLogAnno(module = "角色管理", operation = "根据id删除角色")
	@ResponseBody
	public ResponseResult deleteRole(String id) {
		return roleService.deleteRole(id);
	}
	
	/**
	 * 获取某一个角色的信息
	 * @param id
	 * @return 角色的实体
	 */
	@PostMapping("/getOneRole")
	@ResponseBody
	public ResponseResult getOneRole(String id) {
		return roleService.getOneRole(id);
	}
	
	/**
	 * 更新角色，并更新菜单授权
	 * @param id	菜单id
	 * @param status	状态
	 * @param name	名字
	 * @param code	编码
	 * @param remark	备注
	 * @param rank	排序
	 * @param menuIds	菜单list
	 * @return 返回的结果，0正确ERR500错误
	 */
	@PostMapping("/updateRole")
	@SystemLogAnno(module = "角色管理", operation = "修改角色信息")
	@ResponseBody
	public ResponseResult updateRole(String id,String status,String name,String code,String remark,Integer rank,String menuIds) {
		return roleService.updateRole(id,status, name, code, remark, rank,menuIds);
	}
}
