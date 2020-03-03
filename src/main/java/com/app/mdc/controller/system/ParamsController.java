package com.app.mdc.controller.system;


import com.app.mdc.annotation.anno.SystemLogAnno;
import com.app.mdc.service.system.ParamsService;
import com.app.mdc.utils.viewbean.Page;
import com.app.mdc.utils.viewbean.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since 2019-06-12
 */
@Controller
@RequestMapping("/admin/params")
public class ParamsController {

	private ParamsService paramsService;
	
	@Autowired
	public ParamsController(ParamsService paramsService) {
		this.paramsService=paramsService;
	}
	
	/**
	 * 新增参数
	 * @param name 参数名称
	 * @param keyName 参数键名
	 * @param keyValue 参数键值
	 * @param remark
	 * @return 返回的结果，0正确ERR500错误
	 */
	@PostMapping("/addParams")
	@SystemLogAnno(module = "参数管理", operation = "新增参数")
	@ResponseBody
	public ResponseResult addParams(@RequestParam String status,
									@RequestParam String name,
									@RequestParam String keyName,
									@RequestParam String keyValue, String remark) {
		return paramsService.addParams(status,name, keyName, keyValue, remark);
	}
	
	/**
	 * 查询所有的params并分页
	 * @param page
	 * @param name
	 * @param keyName
	 * @return 参数分页的list
	 */
	@PostMapping("/selectPage")
	@ResponseBody
	public ResponseResult selectPage(Page page,String name,String keyName) {
		return paramsService.selectPage(page, name, keyName);
	}
	
	/**
	 * 获取某一个参数
	 * @param id
	 * @return 参数的实体
	 */
	@PostMapping("/getOne")
	@ResponseBody
	public ResponseResult getOne(String id) {
		return paramsService.getOne(id);
	}
	
	/**
	 * 修改参数
	 * @param id
	 * @param status
	 * @param name 参数名称
	 * @param keyName 参数键名
	 * @param keyValue 参数键值
	 * @return 返回的结果，0正确ERR500错误
	 */
	@PostMapping("/updateParams")
	@SystemLogAnno(module = "参数管理", operation = "修改参数")
	@ResponseBody
	public ResponseResult updateParams(String id,String status,String name,String keyName,String keyValue,String remark) {
		return paramsService.updateParams(id, status, name, keyName, keyValue, remark);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return 返回的结果，0正确ERR500错误
	 */
	@PostMapping("/deleteParams")
	@SystemLogAnno(module = "参数管理", operation = "根据id删除参数")
	@ResponseBody
	public ResponseResult deleteParams(String id) {
		return paramsService.deleteParams(id);
	}
}

