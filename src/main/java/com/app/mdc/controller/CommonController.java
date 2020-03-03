package com.app.mdc.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.app.mdc.enums.ApiErrEnum;
import com.app.mdc.model.system.Dict;
import com.app.mdc.model.system.File;
import com.app.mdc.model.system.Question;
import com.app.mdc.model.system.User;
import com.app.mdc.service.system.DictService;
import com.app.mdc.service.system.FileService;
import com.app.mdc.service.system.QuestionService;
import com.app.mdc.service.system.UserService;
import com.app.mdc.utils.viewbean.Page;
import com.app.mdc.utils.viewbean.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用类controller
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(CommonController.class);

    private final FileService fileService;
    private final DictService dictService;
    private final QuestionService questionService;
    private final UserService userService;

    @Autowired
    public CommonController(FileService fileService, DictService dictService, QuestionService questionService, UserService userService) {
        this.fileService = fileService;
        this.dictService = dictService;
        this.questionService = questionService;
		this.userService = userService;
	}

    /**
     * 文件上传方法
     * @param files file集合
     * @return response
     */
    @PostMapping(value = "upload")
    @ResponseBody
    public ResponseResult uploadFile(MultipartFile[] files,
                                     @RequestParam String moudle,
                                     @RequestParam(defaultValue = "") String moudleType,
                                     @RequestParam(defaultValue = "") String moudleId,
                                     HttpSession httpSession){
        ResponseResult responseResult = new ResponseResult();
        try {
            List<File> filesModel = fileService.uploadFile(files, moudle,moudleType,moudleId, currentUser(httpSession));
            responseResult.setData(filesModel);
        } catch (IOException e) {
            logger.error("系统异常", e);
            responseResult.setErrMsg(ApiErrEnum.ERR500.toString(), ApiErrEnum.ERR500.getDesc()+"文件上传失败！");
        }
        return responseResult;
    }

    /**
     * 根据父级code获取下级子字典
     * @param code code
     * @return ResponseResult
     */
    @PostMapping(value = "getDict")
    @ResponseBody
    public ResponseResult getDict(@RequestParam String code){
        ResponseResult responseResult = new ResponseResult();
        Map<String, Object> params = new HashMap<>();
        params.put("number", code);
        List<Dict> dicts = dictService.getDictsByNumber(params);
        responseResult.setData(dicts);
        return responseResult;
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
	 * 查找所有的user并分页
	 *
	 * @param page 分页插件
	 * @param name 用户姓名
	 * @return 所有user的分页list
	 */
	@RequestMapping("/selectPage")
	@ResponseBody
	public ResponseResult selectPage(Page page, String name) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<User> userList = userService.findUserByPage(name);
		return ResponseResult.success().add(new PageInfo<>(userList));
	}

}
