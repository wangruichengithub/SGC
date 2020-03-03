package com.app.mdc.controller.system;

import com.app.mdc.annotation.anno.SystemLogAnno;
import com.app.mdc.controller.BaseController;
import com.app.mdc.model.system.User;
import com.app.mdc.service.system.MenuService;
import com.app.mdc.utils.viewbean.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/menu")
public class MenuController extends BaseController{
	
	private MenuService menuService;
	
	@Autowired
	public MenuController(MenuService menuService) {
		this.menuService=menuService;
	}
	
	/**
	 * 根据父级菜单id获取下级菜单
	 * @param parentId
	 * @param httpSession
	 * @return 菜单list
	 */
	@PostMapping("/getMenusByPid")
	@ResponseBody
	public ResponseResult getMenusByPid(String parentId,HttpSession httpSession) {
		User user=currentUser(httpSession);
		return menuService.getMenusByPid(parentId,user);
	}
	
	
	/**
	 * 
	 * @param status 状态
	 * @param name	名字
	 * @param icon	图标
	 * @param url	链接
	 * @param level	级别
	 * @param parentId	父级id
	 * @param rank	排序
	 * @return 返回的结果，0正确ERR500错误
	 */
	@PostMapping("/addMenu")
	@SystemLogAnno(module = "菜单管理", operation = "新增菜单信息")
	@ResponseBody
	public ResponseResult addMenu(String status,String name,String icon,String url,Integer level,String parentId,Integer rank) {
		return menuService.addMenu(status, name, icon, url, level, parentId, rank);
	}

	/**
	 * 
	 * @param name 菜单名称
	 * @return 菜单list转化的树形结构
	 */
	@PostMapping("/menuList")
	@ResponseBody
	public ResponseResult menuList(String name) {
		return menuService.menuList(name);
	}
	
	/**
	 * 删除菜单
	 * @param id 菜单id
	 * @return 返回的结果，0正确ERR500错误
	 */
	@PostMapping("/delMenu")
	@SystemLogAnno(module = "菜单管理", operation = "根据id删除菜单信息")
	@ResponseBody
	public ResponseResult delMenu(@RequestParam String id) {
		return menuService.delMenu(id);
	}
	
	
	/**
	 * 
	 * @param id 根据菜单id获取菜单详细信息
	 * @return 菜单实体类
	 */
	@PostMapping("/getOneMenu")
	@ResponseBody
	public ResponseResult getOneMenu(@RequestParam String id) {
		return menuService.getOneMenu(id);
	}
	
	/**
	 * 
	 * @param id 根据菜单id修改
	 * @param status	状态
	 * @param name	菜单名字
	 * @param icon	图标	
	 * @param url	链接
	 * @param level	级别
	 * @param parentId	父级id
	 * @param rank	排序
	 * @return 返回的结果，0正确ERR500错误
	 */ 
	@PostMapping("/updateMenu")
	@SystemLogAnno(module = "菜单管理", operation = "修改菜单信息")
	@ResponseBody
	public ResponseResult updateMenu(String id,String status,String name,String icon,String url,Integer level,String parentId,Integer rank){
		return menuService.updateMenu(id, status, name, icon, url, level, parentId, rank);
	}
	
	
}
