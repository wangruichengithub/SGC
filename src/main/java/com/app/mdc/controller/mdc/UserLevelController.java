package com.app.mdc.controller.mdc;

import com.app.mdc.model.system.User;
import com.app.mdc.service.system.UserLevelService;
import com.app.mdc.service.system.UserService;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工会信息
 */
@RestController
@RequestMapping("/userLevel")
@Api("工会信息")
public class UserLevelController {

    @Autowired
    private UserLevelService userLevelService;

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    @ApiOperation("/工会列表")
    public ResponseResult list(@RequestParam Integer userId,@RequestParam Integer pageNumber,@RequestParam Integer pageSize) {
        Page<Map> page = PageHelper.startPage(pageNumber, pageSize);
        List<Map<String,Object>> members =  this.userLevelService.getGhztList(userId);
        User user = userService.selectById(userId);
//        Integer cardNumber = userLevelService.getUnionCardNumber(userId);
        Map<String,Object> result = new HashMap<>();
        result.put("memberSize",user.getMemberSize());
        result.put("cardNumber",0);
        result.put("unionSignTotalMoney",user.getUnionSignTotalMoney());
        result.put("memberList",members);
        return ResponseResult.success().setData(result);
    }

}
