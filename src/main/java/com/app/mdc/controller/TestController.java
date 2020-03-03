package com.app.mdc.controller;

import com.app.mdc.mapper.mdc.VasWalletMapper;
import com.app.mdc.model.mdc.VasWallet;
import com.app.mdc.utils.httpclient.vas.CoinUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Api("核心接口")
@RequestMapping("/test")
public class TestController {
    @Autowired
    private CoinUtils coinUtils;
    private VasWalletMapper vasWalletMapper;

    @RequestMapping("address")
    public String test() throws Throwable {
        VasWallet vasWallet = coinUtils.createaddress(123);
        vasWalletMapper.insert(vasWallet);
        return null;
    }
}
