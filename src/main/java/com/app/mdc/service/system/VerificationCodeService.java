package com.app.mdc.service.system;

import com.app.mdc.exception.BusinessException;
import com.app.mdc.model.system.VerificationCode;
import com.baomidou.mybatisplus.service.IService;

public interface VerificationCodeService extends IService<VerificationCode> {

    /**
     * 短信推送
     * @return
     * @param userId
     */
    Integer getVerificationCode(Integer userId) throws BusinessException;

    /**
     * 获取email验证码
     * @return
     * @param email
     * @param kind
     */
    Integer getEmailVerificationCode(String email, Integer kind) throws BusinessException;

    /**
     * 验证验证码是狗正确
     * @param verCode
     * @param verId
     * @return
     */
    boolean validateVerCode(String verCode, String verId);

    /**
     * 获取手机验证码
     * @param phone
     * @param kind
     * @return
     */
    Integer getPhoneVerificationCode(String phone, Integer kind) throws BusinessException;
}
