package com.app.mdc.serviceImpl.system;


import com.app.mdc.config.mail.MailProperties;
import com.app.mdc.exception.BusinessException;
import com.app.mdc.mapper.system.VerificationCodeMapper;
import com.app.mdc.model.system.User;
import com.app.mdc.model.system.VerificationCode;
import com.app.mdc.service.system.UserService;
import com.app.mdc.service.system.VerificationCodeService;
import com.app.mdc.utils.httpclient.HttpUtil;
import com.app.mdc.utils.verification.RandomValidateCodeUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VerificationCodeServiceImpl extends ServiceImpl<VerificationCodeMapper, VerificationCode> implements VerificationCodeService {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    @Override
    public Integer getVerificationCode(Integer userId) throws BusinessException {
        //获取当前用户
        User user = userService.selectById(userId);
        if(user == null){
            throw new BusinessException("用户不存在");
        }

        if(user.getRegisterType() == 0){
            //邮箱
            return this.getEmailVerificationCode(user.getLoginName(), 0);
        }else{
            //手机
            return this.getPhoneVerificationCode(user.getLoginName(), 0);
        }
    }

    @Override
    public Integer getEmailVerificationCode(String email, Integer kind) throws BusinessException {
        //找回密码 判断用户是否存在
        if(kind == 10){
            EntityWrapper<User> userEntityWrapper = new EntityWrapper<>();
            userEntityWrapper.eq("del_flag","0");
            userEntityWrapper.eq("login_name",email);
            List<User> users = userService.selectList(userEntityWrapper);
            if(users.size() == 0){
                throw new BusinessException("该账号不存在");
            }
        }


        //生成验证码入库
        String randcode = RandomValidateCodeUtil.getRandcode();
        VerificationCode verificationCode = new VerificationCode(randcode, "游客", new Date());
        this.baseMapper.insert(verificationCode);

        //推送验证码给用户邮箱
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getUsername());
        message.setTo(email); // 接收地址,可传入数组进行群发
        message.setSubject("MDC验证码"); // 标题
        String content = "尊敬的用户您的验证码是"+ randcode +"请不要把验证码泄漏给其他人,如非本人请勿操作";
        message.setText(content); // 内容
        javaMailSender.send(message);
        return verificationCode.getId();
    }

    @Override
    public Integer getPhoneVerificationCode(String phone, Integer kind) throws BusinessException {
        //找回密码 判断用户是否存在
        if(kind == 10){
            EntityWrapper<User> userEntityWrapper = new EntityWrapper<>();
            userEntityWrapper.eq("del_flag","0");
            userEntityWrapper.eq("login_name",phone);
            List<User> users = userService.selectList(userEntityWrapper);
            if(users.size() == 0){
                throw new BusinessException("该账号不存在");
            }
        }
        //生成验证码入库
        String randcode = RandomValidateCodeUtil.getRandcode();
        VerificationCode verificationCode = new VerificationCode(randcode, "游客", new Date());
        this.baseMapper.insert(verificationCode);

        //推送验证码
        String content = "尊敬的用户您的验证码是"+ randcode +"请不要把验证码泄漏给其他人,如非本人请勿操作";
        String url = null;
        try {
            url = "https://mb345.com/ws/BatchSend2.aspx?CorpID=XALKJ0006852&Pwd=zh9527@&Mobile="+ phone +"&Content="
                    + URLEncoder.encode(content,"gb2312") + "&SendTime=";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try{
            Map<String,Object> map = new HashMap<>();
            HttpUtil.doGet(url,map);
        }
        catch (Exception e){
            throw  new BusinessException("验证码获取失败");
        }
        return verificationCode.getId();
    }


    @Override
    public boolean validateVerCode(String verCode, String verId)  {
        VerificationCode verificationCode = this.baseMapper.selectById(verId);
        if(verificationCode == null){
            return false;
        }
        //验证成功后删除该验证码
        this.baseMapper.deleteById(verificationCode.getId());
        return verCode.equals(verificationCode.getCode());
    }

}
