package com.app.mdc.controller.mdc;

import com.app.mdc.model.mdc.InCome;
import com.app.mdc.model.mdc.Transaction;
import com.app.mdc.service.mdc.InComeService;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/income")
@Api("收益管理")
public class InComeController {

    @Autowired
    private InComeService inComeService;

    @PostMapping("/list")
    @ResponseBody
    @ApiOperation("/收益列表")
    public ResponseResult list(@RequestParam Integer pageSize,@RequestParam Integer pageNumber,@RequestParam Integer userId ) {
//        PageHelper.startPage(pageNumber,pageSize);
        List<InCome.IncomeNode> list = inComeService.list(userId,pageSize,pageNumber);
        return ResponseResult.success().setData((list));
    }

    @PostMapping("/history")
    @ResponseBody
    @ApiOperation("/交易记录")
    public ResponseResult history(@RequestParam Integer pageSize,@RequestParam Integer pageNumber,@RequestParam Integer userId ) {
        PageHelper.startPage(pageNumber,pageSize);
        List<Transaction> list = inComeService.history(userId);
        return ResponseResult.success().setData(new PageInfo<Transaction>(list));
    }
}
