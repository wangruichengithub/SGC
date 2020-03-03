package com.app.mdc.serviceImpl.mdc;

import com.app.mdc.mapper.mdc.FeedBackMapper;
import com.app.mdc.model.mdc.FeedBack;
import com.app.mdc.model.system.User;
import com.app.mdc.service.mdc.FeedBackService;
import com.app.mdc.service.system.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2020-02-05
 */
@Service
public class FeedBackServiceImpl extends ServiceImpl<FeedBackMapper, FeedBack> implements FeedBackService {

    @Autowired
    private UserService userService;

    @Override
    public void add(String userId, String message) {
        User user = userService.selectById(userId);
        FeedBack feedBack = new FeedBack();
        feedBack.setMessage(message);
        feedBack.setCreateBy(user.getLoginName());
        feedBack.setCreateTime(new Date());
        this.baseMapper.insert(feedBack);
    }
}
