package com.app.mdc.service.system;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.app.mdc.model.system.District;
import com.app.mdc.utils.viewbean.ResponseResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since 2019-06-17
 */
public interface DistrictService extends IService<District> {

	/**
	 * 新增行政区
	 * @param map 不包括id的行政区实体类
	 * @return 0正确ERR500错误
	 */
	ResponseResult add(Map<String, Object> map);
	
	/**
	 * 获取所有的行政区
	 * @param map code编码 name名字
	 * @return 行政区list
	 */
	List<District> findDistricts(Map<String, Object> map);
	
	/**
	 * 获取某一个行政区
	 * @param map id 行政区id
	 * @return 行政区实体
	 */
	District getOne(Map<String, Object> map);
	
	/**
	 * 修改
	 * @param map 包括id的行政区实体类
	 * @return 0正确ERR500错误
	 */
	ResponseResult updateDistrict(Map<String, Object> map);
	
	/**
	 * 删除
	 * @param map 包括id的行政区实体类
	 * @return 0正确ERR500错误
	 */
	ResponseResult deleteDistrict(Map<String, Object> map);
}
