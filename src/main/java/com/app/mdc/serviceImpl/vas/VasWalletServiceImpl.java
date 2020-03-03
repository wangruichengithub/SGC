/*
package com.app.mdc.serviceImpl.vas;

import com.alibaba.fastjson.JSONObject;
import com.app.mdc.service.vas.VasWalletService;
import com.app.mdc.utils.httpclient.HttpUtil;
import com.app.mdc.utils.httpclient.vas.Constants;
import com.app.mdc.utils.viewbean.ResponseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VasWalletServiceImpl implements VasWalletService {
    @Value(value = "${vas.walletUrl}")
    private  String ip;
    @Override
    public ResponseResult createWallet(int userId) {
        Constants constants = Constants.builder()
                .method("createaddresses")
                .params(1)
                .build();
        String result = HttpUtil.doPostJson(ip, JSONObject.toJSONString(constants));
        System.out.println(result);

        return null;
    }
}

*/
