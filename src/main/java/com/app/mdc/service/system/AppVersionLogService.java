package com.app.mdc.service.system;

import com.baomidou.mybatisplus.service.IService;
import com.app.mdc.model.system.AppVersionLog;
import com.app.mdc.utils.viewbean.ResponseResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since 2019-08-15
 */
public interface AppVersionLogService extends IService<AppVersionLog> {

	/**
	 * 获取版本list
	 * @param map	versionNumber版本号
	 * @return 版本list
	 */
	List<AppVersionLog> getList(Map<String,Object> map);

	/**
	 * 新增
	 * @param map	versionNumber版本号
	 * @return	返回正确错误信息
	 */
	ResponseResult addAppVersionLog(Map<String,Object> map);


	/**
	 * 获取单个版本信息
	 * @param id 版本id
	 * @return 版本实体类
	 */
	AppVersionLog getOne(String id);
}
