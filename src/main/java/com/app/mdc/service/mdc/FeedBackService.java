package com.app.mdc.service.mdc;

import com.app.mdc.model.mdc.FeedBack;
import com.app.mdc.model.mdc.Wallet;
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
public interface FeedBackService extends IService<FeedBack> {

    /**
     * 添加反馈信息
     * @param userId
     * @param message
     */
    void add(String userId, String message);
}
