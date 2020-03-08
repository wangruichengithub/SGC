package com.app.mdc.controller.mdc;

import com.app.mdc.exception.BusinessException;
import com.app.mdc.service.mdc.UserContractService;
import com.app.mdc.service.system.UserService;
import com.app.mdc.service.system.VerificationCodeService;
import com.app.mdc.utils.viewbean.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 用户合同接口
 */
@RestController
@RequestMapping("/userContract")
@Api(description = "用户合约模块")
public class UserContractController {

    @Autowired
    private UserContractService userContractService;
    @Autowired
    private UserService userService;
    @Autowired
    private VerificationCodeService verificationCodeService;
    private Integer contractId;

    @RequestMapping(value = "/add",method = POST)
    @ApiOperation("新增用户合同")
    public ResponseResult add(@RequestParam String payPassword,@RequestParam String verCode ,@RequestParam String verId ,@RequestParam Integer userId,@RequestParam Integer contractId,@RequestParam Integer number) throws BusinessException {
        userService.validatePayPassword(userId,payPassword);
        /*if(!verificationCodeService.validateVerCode(verCode,verId)){
            throw new BusinessException("验证码校验错误");
        }*/
        if (contractId==4||contractId.equals(4)){
            return ResponseResult.fail("ERR500","该合约暂未开放");
        }else {
            userContractService.add(userId,contractId,number);
            return ResponseResult.success();
        }

    }

    @RequestMapping(value = "/getUpgradePriceDifference",method = POST)
    @ApiOperation("获取合约升级差价")
    public ResponseResult getUpgradePriceDifference(@RequestParam Integer ucId,@RequestParam Integer upgradeId) throws BusinessException {
        BigDecimal price = userContractService.getUpgradePriceDifference(ucId,upgradeId);
        Map<String,Object> result = new HashMap<>();
        result.put("priceDifference",price);
        return ResponseResult.success().setData(result);
    }

    @RequestMapping(value = "/upgrade",method = POST)
    @ApiOperation("合约升级")
    public ResponseResult upgrade(@RequestParam String payPassword,@RequestParam String verCode ,@RequestParam String verId ,@RequestParam Integer userId,@RequestParam Integer ucId,@RequestParam Integer upgradeId) throws BusinessException {
        userService.validatePayPassword(userId,payPassword);
        /*if(!verificationCodeService.validateVerCode(verCode,verId)){
            throw new BusinessException("验证码校验错误");
        }*/
        userContractService.upgrade(userId,ucId,upgradeId);
        return ResponseResult.success();
    }

    @RequestMapping(value = "/getRescindMoney",method = POST)
    @ApiOperation("获取解约金额")
    public ResponseResult getRescindMoney(@RequestParam Integer contractId) throws BusinessException {
        BigDecimal price = userContractService.getRescindMoney(contractId);
        Map<String,Object> result = new HashMap<>();
        result.put("rescindMoney",price);
        return ResponseResult.success().setData(result);
    }


    @RequestMapping(value = "/rescind",method = POST)
    @ApiOperation("合约解约")
    public ResponseResult rescind(@RequestParam String payPassword,@RequestParam String verCode ,@RequestParam String verId ,@RequestParam Integer userId,@RequestParam Integer ucId) throws BusinessException {
        userService.validatePayPassword(userId,payPassword);
        /*if(!verificationCodeService.validateVerCode(verCode,verId)){
            throw new BusinessException("验证码校验错误");
        }*/
        userContractService.rescind(userId,ucId);
        return ResponseResult.success();
    }

    @RequestMapping(value = "/getHigherContract",method = POST)
    @ApiOperation("获取高等级的用户合约")
    public ResponseResult getHigherContract(@RequestParam String contractId) {
        return ResponseResult.success().setData( userContractService.getHigherContract(contractId));
    }


}
