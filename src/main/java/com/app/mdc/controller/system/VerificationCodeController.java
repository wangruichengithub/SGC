package com.app.mdc.controller.system;

import com.app.mdc.exception.BusinessException;
import com.app.mdc.service.system.VerificationCodeService;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * 验证码管理
 */
@RestController
@RequestMapping("/verificationCode")
@Api("验证码管理")
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    @RequestMapping("/getVerificationCode")
    @ApiOperation("/获取验证码")
    public ResponseResult getVerificationCode(@RequestParam Integer kind,@RequestParam Integer type,@RequestParam(required = false) String phone,@RequestParam(required = false) String email) throws BusinessException {
        //kine = 10 找回面貌
        Map<String,Object> result = new HashMap<>();
        if(type == 0){
            //邮箱验证
            if(StringUtils.isEmpty(email)){
                throw new BusinessException("请输入邮箱");
            }
            result.put("verId",verificationCodeService.getEmailVerificationCode(email,kind));
        }else if(type == 1){
            if(StringUtils.isEmpty(phone)){
                throw new BusinessException("请输入手机号");
            }
            //手机验证
            result.put("verId",verificationCodeService.getPhoneVerificationCode(phone,kind));
        }
        return ResponseResult.success().setData(result);
    }

    @RequestMapping("/getVerificationCodeByUserId")
    @ApiOperation("/获取验证码")
    public ResponseResult getVerificationCodeByUserId(@RequestParam Integer userId) throws BusinessException {
        Integer id = verificationCodeService.getVerificationCode(userId);
        Map<String,Object> result = new HashMap<>();
        result.put("verId",id);
        return ResponseResult.success().setData(result);
    }

}
