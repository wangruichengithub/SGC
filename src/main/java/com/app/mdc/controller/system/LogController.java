package com.app.mdc.controller.system;


import com.app.mdc.controller.BaseController;
import com.app.mdc.service.system.LogService;
import com.app.mdc.utils.viewbean.Page;
import com.app.mdc.utils.viewbean.ResponseResult;
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
 * @since 2019-08-01
 */
@Controller
@RequestMapping("/admin/log")
public class LogController extends BaseController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    /**
     * 分页获取log日志
     * @param page 分页插件
     * @param map 查询参数
     * @return ResponseResult
     */
    @PostMapping("/json")
    @ResponseBody
    public ResponseResult getLogPage(Page page, @RequestParam Map<String,Object> map){
        return logService.findLogsPage(page, map);
    }

	/**
	 * 获取单个日志详细信息
	 * @param id	日志id
	 * @return	日志实体类
	 */
	@PostMapping("/getOneLog")
    @ResponseBody
    public ResponseResult getOneLog(@RequestParam String id){
        return ResponseResult.success().setData(logService.getOneLog(id));
    }
}

