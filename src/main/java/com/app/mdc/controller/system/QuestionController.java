package com.app.mdc.controller.system;


import com.app.mdc.annotation.anno.SystemLogAnno;
import com.app.mdc.controller.BaseController;
import com.app.mdc.model.system.Question;
import com.app.mdc.service.system.QuestionService;
import com.app.mdc.utils.viewbean.Page;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since 2019-06-19
 */
@Controller
@RequestMapping("/admin/question")
public class QuestionController extends BaseController {
	
	private QuestionService questionService;
	
	@Autowired
	public QuestionController(QuestionService questionService) {
		this.questionService=questionService;
	}

	/**
	 * 新增
	 * @param map status 状态 	questionTitle 问题标题    questionSolution  解决方案
	 * @return 返回的结果，0正确ERR500错误
	 */
	@PostMapping("/addQuestion")
	@SystemLogAnno(module = "常见问题管理", operation = "新增常见问题")
	@ResponseBody
	public ResponseResult addQuestion(@RequestParam Map<String, Object> map) {
		return questionService.addQuestion(map);
	}
	
	/**
	 * 获取所有问题(分页)
	 * @param map  id  问题id   questionTitle 问题标题
	 * @return 问题list
	 */
	@PostMapping("/getAll")
	@ResponseBody
	public ResponseResult getAll(@RequestParam Map<String, Object> map, Page page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<Question> list=questionService.getAll(map);
		return ResponseResult.success().add(new PageInfo<>(list));
	}
	
	/**
	 * 获取某一个问题
	 * @param map id  问题id
	 * @return 问题实体
	 */
	@PostMapping("/getOne")
	@ResponseBody
	public ResponseResult getOne(@RequestParam Map<String, Object> map) {
		Question question=questionService.getOne(map);
		return ResponseResult.success().add(question);
	}
	
	/**
	 * 修改
	 * @param map   id  问题id  status 状态 	questionTitle 问题标题    questionSolution  解决方案
	 * @return 返回的结果，0正确ERR500错误
	 */
	@PostMapping("/updateQuestion")
	@SystemLogAnno(module = "常见问题管理", operation = "修改常见问题")
	@ResponseBody
	public ResponseResult updateQuestion(@RequestParam Map<String, Object> map) {
		return questionService.updateQuestion(map);
	}
	
	/**
	 * 删除
	 * @param map  id  问题id 
	 * @return 返回的结果，0正确ERR500错误
	 */
	@PostMapping("/deleteQuestion")
	@SystemLogAnno(module = "常见问题管理", operation = "删除常见问题")
	@ResponseBody
	public ResponseResult deleteQuestion(@RequestParam Map<String, Object> map) {
		return questionService.deleteQuestion(map);
	}

	/**
	 * 导出word
	 * @param map
	 * @return
	 */
	@PostMapping("/exportWord")
	@ResponseBody
	public ResponseResult exportWord(@RequestParam Map<String, Object> map, HttpSession httpSession) {
		return questionService.exportWord(map,currentUser(httpSession));
	}
}

