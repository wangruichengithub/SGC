package com.app.mdc.controller.system;


import com.app.mdc.annotation.anno.SystemLogAnno;
import com.app.mdc.model.system.Dict;
import com.app.mdc.service.system.DictService;
import com.app.mdc.utils.viewbean.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since 2019-06-12
 */
@Controller
@RequestMapping("/admin/dict")
public class DictController {
	
	private DictService dictService;
	
	@Autowired
	public DictController(DictService dictService) {
		this.dictService=dictService;
	}
	
	/**
	 * 新增
	 * @param map name 字典名称  pid 父级字典id type 字典类型 number 字典编号 desc 字典描述 rank 排序 status
	 * @return 返回的结果，0正确ERR500错误
	 */
	@PostMapping("/addDict")
	@SystemLogAnno(module = "字典管理", operation = "新增字典信息")
	@ResponseBody
	public ResponseResult addDict(@RequestParam Map<String, Object> map) {
		return dictService.addDict(map);
	}
	
	/**
	 * 获取字典list并转化树形
	 * @param map name 字典名称
	 * @return 字典list的树形结构
	 */
	@PostMapping("/selectTree")
	@ResponseBody
	public ResponseResult selectTree(@RequestParam Map<String, Object> map) {
		return dictService.selectTree(map);
	}
	
	/**
	 * 获取某一个字典
	 * @param map id 字典id
	 * @return 字典实体类
	 */
	@PostMapping("/getOne")
	@ResponseBody
	public ResponseResult getOne(@RequestParam Map<String, Object> map) {
		Dict dict=dictService.getOne(map);
		return ResponseResult.success().add(dict);
	}
	
	/**
	 * 修改
	 * @param map name 字典名称  pid 父级字典id type 字典类型 number 字典编号 desc 字典描述 rank 排序 status 状态 id 字典id
	 * @return 返回的结果，0正确ERR500错误
	 */
	@PostMapping("/updateDict")
	@SystemLogAnno(module = "字典管理", operation = "修改字典信息")
	@ResponseBody
	public ResponseResult updateDict(@RequestParam Map<String, Object> map) {
		return dictService.updateDict(map);
	}
	
	/**
	 * 删除
	 * @param map  id 字典id
	 * @return 返回的结果，0正确ERR500错误
	 */
	@PostMapping("/deleteDict")
	@SystemLogAnno(module = "字典管理", operation = "沙暗楚字典信息")
	@ResponseBody
	public ResponseResult deleteDict(@RequestParam Map<String, Object> map) {
		return dictService.deleteDict(map);
	}
	
	@PostMapping("/getDictsByNumber")
	@ResponseBody
	public ResponseResult getDictsByNumber(@RequestParam Map<String, Object> map) {
		return ResponseResult.success().add(dictService.getDictsByNumber(map));
	}
}

