package com.app.mdc.service.vas;

import com.app.mdc.utils.viewbean.ResponseResult;

public interface VasWalletService {

    /**
     * 创建vas 钱包
     * @param userId
     * @return
     */
    ResponseResult createWallet (int userId);

}
