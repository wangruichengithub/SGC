package com.app.mdc.controller.system;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.app.mdc.annotation.anno.SystemLogAnno;
import com.app.mdc.model.system.District;
import com.app.mdc.service.system.DistrictService;
import com.app.mdc.utils.TreeUtils;
import com.app.mdc.utils.viewbean.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since 2019-06-17
 */
@Controller
@RequestMapping("/admin/district")
public class DistrictController {
	
	private DistrictService districtService;
	
	@Autowired
	public DistrictController(DistrictService districtService) {
		this.districtService=districtService;
	}

	/**
	 * 新增行政区
	 * @param map 不包括id的行政区实体类
	 * @return 0正确ERR500错误
	 */
	@PostMapping("/addDistrict")
	@SystemLogAnno(module = "区域管理", operation = "新增区域信息")
	@ResponseBody
	public ResponseResult addDistrict(@RequestParam Map<String, Object> map) {
		return districtService.add(map);
	}
	
	/**
	 * 获取所有的行政区
	 * @param map code编码 name名字
	 * @return 行政区list
	 */
	@PostMapping("/findDistricts")
	@ResponseBody
	public ResponseResult findDistricts(@RequestParam Map<String, Object> map) {
		List<District> list=districtService.findDistricts(map);
		JSONArray result = TreeUtils.listToTree(JSONArray.parseArray(JSON.toJSONString(list)),"id","parentId","childrens");
		return ResponseResult.success().add(result);
	}
	
	/**
	 * 获取某一个行政区
	 * @param map id 行政区id
	 * @return 行政区实体
	 */
	@PostMapping("/getOne")
	@ResponseBody
	public ResponseResult getOne(@RequestParam Map<String, Object> map) {
		District district=districtService.getOne(map);
		return ResponseResult.success().add(district);
	}
	
	/**
	 * 修改
	 * @param map 包括id的行政区实体类
	 * @return 0正确ERR500错误
	 */
	@PostMapping("/updateDistrict")
	@SystemLogAnno(module = "区域管理", operation = "修改区域信息")
	@ResponseBody
	public ResponseResult updateDistrict(@RequestParam Map<String, Object> map) {
		return districtService.updateDistrict(map);
	}
	
	/**
	 * 删除
	 * @param map 包括id的行政区实体类
	 * @return 0正确ERR500错误
	 */
	@PostMapping("/deleteDistrict")
	@SystemLogAnno(module = "区域管理", operation = "id删除区域信息")
	@ResponseBody
	public ResponseResult deleteDistrict(@RequestParam Map<String, Object> map) {
		return districtService.deleteDistrict(map);
	}
}

