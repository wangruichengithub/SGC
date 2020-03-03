package com.app.mdc.service.system;

import com.baomidou.mybatisplus.service.IService;
import com.app.mdc.model.system.Params;
import com.app.mdc.utils.viewbean.Page;
import com.app.mdc.utils.viewbean.ResponseResult;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since 2019-06-12
 */
public interface ParamsService extends IService<Params> {

	/**
	 * 新增参数
	 * @param name 参数名称
	 * @param keyName 参数键名
	 * @param keyValue 参数键值
	 * @param remark
	 */
	ResponseResult addParams(String status,String name,String keyName,String keyValue,String remark);
	
	/**
	 * 查询所有的params并分页
	 * @param page
	 * @param name
	 * @param keyName
	 * @return
	 */
	ResponseResult selectPage(Page page,String name,String keyName);
	
	/**
	 * 获取某一个参数
	 * @param id
	 * @return
	 */
	ResponseResult getOne(String id);
	
	/**
	 * 修改参数
	 * @param id
	 * @param status
	 * @param name 参数名称
	 * @param keyName 参数键名
	 * @param keyValue 参数键值
	 * @return
	 */
	ResponseResult updateParams(String id,String status,String name,String keyName,String keyValue,String remark);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	ResponseResult deleteParams(String id);

	/**
	 * 获取系统参数接口
	 * @return map，系统参数所有相关值,keyName:keyValue
	 */
    Map<String, String> findSystemParams();

	/**
	 * 根据键值获取参数
	 * @param keyName 键值
	 * @return 参数
	 */
	Params getAppParam(String keyName);

}
