package com.app.mdc.controller.system;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.app.mdc.model.system.AppVersionLog;
import com.app.mdc.service.system.AppVersionLogService;
import com.app.mdc.utils.viewbean.Page;
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
 * @since 2019-08-15
 */
@Controller
@RequestMapping("/admin/appVersionLog")
public class AppVersionLogController {

	private AppVersionLogService appVersionLogService;

	@Autowired
	public AppVersionLogController(AppVersionLogService appVersionLogService){
		this.appVersionLogService=appVersionLogService;
	}

	/**
	 * 获取版本list
	 * @param map	versionNumber版本号
	 * @return 版本list
	 */
	@PostMapping("/getList")
	@ResponseBody
	public ResponseResult getList(@RequestParam Map<String, Object> map, Page page){
		PageHelper.startPage(page.getPageNum(),page.getPageSize());
		List<AppVersionLog> logList=appVersionLogService.getList(map);
		return ResponseResult.success().add(new PageInfo<>(logList));
	}

	/**
	 * 获取单个版本信息
	 * @param id 版本id
	 * @return 版本实体类
	 */
	@PostMapping("/getOne")
	@ResponseBody
	public ResponseResult getOne(@RequestParam String id){
		return ResponseResult.success().add(appVersionLogService.getOne(id));
	}

	/**
	 * 新增
	 * @param map	versionNumber版本号	versionInfo版本信息
	 * @return	返回正确错误信息
	 */
	@PostMapping("/addAppVersionLog")
	@ResponseBody
	public ResponseResult addAppVersionLog(@RequestParam Map<String, Object> map){
		return appVersionLogService.addAppVersionLog(map);
	}
}

