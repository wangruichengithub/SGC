package com.app.mdc.service.system;

import com.baomidou.mybatisplus.service.IService;
import com.app.mdc.model.system.Question;
import com.app.mdc.model.system.User;
import com.app.mdc.utils.viewbean.ResponseResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since 2019-06-19
 */
public interface QuestionService extends IService<Question> {

	/**
	 * 新增
	 * @param map status 状态 	questionTitle 问题标题    questionSolution  解决方案
	 * @return 返回的结果，0正确ERR500错误
	 */
	ResponseResult addQuestion(Map<String, Object> map);
	
	/**
	 * 获取所有问题
	 * @param map  id  问题id   questionTitle 问题标题
	 * @return 问题list
	 */
	List<Question> getAll(Map<String, Object> map);
	
	/**
	 * 获取某一个问题
	 * @param map id  问题id
	 * @return 问题实体
	 */
	Question getOne(Map<String, Object> map);
	
	/**
	 * 修改
	 * @param map   id  问题id  status 状态 	questionTitle 问题标题    questionSolution  解决方案
	 * @return 返回的结果，0正确ERR500错误
	 */
	ResponseResult updateQuestion(Map<String, Object> map);
	
	/**
	 * 删除
	 * @param map  id  问题id 
	 * @return 返回的结果，0正确ERR500错误
	 */
	ResponseResult deleteQuestion(Map<String, Object> map);

	/**
	 * 导出word
	 * @param map questionType 问题类型
	 * @return ResponseResult
	 */
	ResponseResult exportWord(Map<String, Object> map, User user);
}
