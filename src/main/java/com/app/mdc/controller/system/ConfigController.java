package com.app.mdc.controller.system;


import com.app.mdc.service.system.ConfigService;
import com.app.mdc.utils.viewbean.Page;
import com.app.mdc.utils.viewbean.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * <p>
 * 参数配置表 前端控制器
 * </p>
 *
 * @author 
 * @since 2020-02-09
 */
@Controller
@RequestMapping("/admin/config")
public class ConfigController {

    private ConfigService configService;

    @Autowired
    public ConfigController(ConfigService configService){
        this.configService = configService;
    }

    /**
     * 查询所有的params并分页
     * @param page
     * @return 参数分页的list
     */
    @PostMapping("/selectPage")
    @ResponseBody
    public ResponseResult selectPage(Page page,@RequestParam Map<String,Object> params) {
        return configService.getConfig(page,params);
    }
}

