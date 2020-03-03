package com.app.mdc.service.system;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.app.mdc.model.system.Dict;
import com.app.mdc.utils.viewbean.ResponseResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since 2019-06-12
 */
public interface DictService extends IService<Dict> {
	
	/**
	 * 新增
	 * @param map name 字典名称  pid 父级字典id type 字典类型 number 字典编号 desc 字典描述 rank 排序 status
	 * @return 返回的结果，1正确0错误
	 */
	ResponseResult addDict(Map<String, Object> map);
	
	/**
	 * 获取某一个字典
	 * @param map id 字典id
	 * @return 字典实体类
	 */
	Dict getOne(Map<String, Object> map);
	
	
	/**
	 * 获取字典list
	 * @param map id 字典id
	 * @return 字典list
	 */
	List<Dict> getList(Map<String, Object> map);
	
	/**
	 * 获取字典list并转化树形
	 * @param map name 字典名称
	 * @return 字典list的树形结构
	 */
	ResponseResult selectTree(Map<String, Object> map);
	
	/**
	 * 修改
	 * @param map name 字典名称  pid 父级字典id type 字典类型 number 字典编号 desc 字典描述 rank 排序 status 状态 id 字典id
	 * @return 返回的结果，1正确0错误
	 */
	ResponseResult updateDict(Map<String, Object> map);
	
	/**
	 * 删除
	 * @param map  id 字典id
	 * @return 返回的结果，1正确0错误
	 */
	ResponseResult deleteDict(Map<String, Object> map);
	
	/**
	 * 根据编号找所有下级的字典
	 * @param map number编号
	 * @return
	 */
	List<Dict> getDictsByNumber(Map<String, Object> map);
}
