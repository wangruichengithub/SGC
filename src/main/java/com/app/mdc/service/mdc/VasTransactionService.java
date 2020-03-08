package com.app.mdc.service.mdc;

import com.app.mdc.model.mdc.Transaction;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-02-05
 */
public interface VasTransactionService extends IService<Transaction> {

    /**
     * 转账
     * @return
     * @throws Throwable
     */
    ResponseResult tranfer(String userId, String payPassword, String toAddress, String transferNumber, String verCode, String verId)throws Throwable;

    /**
     * usdt闪兑
     * @param userId 用户id
     * @param convertMoney 兑换数量
     * @return ResponseResult
     */
    ResponseResult convertUsdt(String userId,String convertMoney,String payPassword);
}
