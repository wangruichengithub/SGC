package com.app.mdc.service.mdc;

import com.app.mdc.model.mdc.Wallet;
import com.app.mdc.model.system.User;
import com.app.mdc.utils.viewbean.Page;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.baomidou.mybatisplus.service.IService;
import org.web3j.crypto.CipherException;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-02-05
 */
public interface WalletService extends IService<Wallet> {

    /**
     * 创建钱包
     * @param userId 用户信息
     * @return ResponseResult
     */
    ResponseResult createWallet(int userId,String password) throws Throwable;


    /**
     * 获取钱包余额
     * @param page 分页插件
     * @param params 查询条件
     * @return ResponseResult
     */
    ResponseResult getBalance(Page page, Map<String,Object> params);
}
