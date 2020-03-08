package com.app.mdc.serviceImpl.system;


import com.app.mdc.mapper.mdc.UserContractMapper;
import com.app.mdc.mapper.system.UserLevelMapper;
import com.app.mdc.model.system.*;
import com.app.mdc.service.system.UserLevelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserLevelServiceImpl extends ServiceImpl<UserLevelMapper, UserLevel> implements UserLevelService {

    @Autowired
    private UserContractMapper userContractMapper;
    @Override
    public void addLevelRelation(String recIds, String recedId) {
        if (StringUtils.isEmpty(recIds)) {
            return;
        }
        String[] split = recIds.split(",");
        List<UserLevel> userLevels = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            UserLevel userLevel = new UserLevel(split[i], recedId, split.length - i, new Date());
            userLevels.add(userLevel);
        }
        this.baseMapper.batchInsert(userLevels);
    }

    @Override
    public Map<Integer,  Map<String,Object>> selectRecedUserIds(Integer userId) {
        return this.baseMapper.selectRecedUserIds(userId);
    }

    @Override
    public BigDecimal getTotalSum(Integer userId) {
        return this.baseMapper.getTotalSum(userId);
    }

    @Override
    public List<Integer> selectRecIdsByRecedId(Integer userId) {
        return this.baseMapper.selectRecIdsByRecedId(userId);
    }

    @Override
    public Integer getDirectCount(Integer userId) {
        return this.baseMapper.getDirectCount(userId);
    }

    @Override
    public List<User> getDirectUsers(Integer userId) {
        return this.baseMapper.getDirectUsers(userId);
    }

    @Override
    public Integer selectMemberSizeByUserId(String userId) {
        return this.baseMapper.selectMemberSizeByUserId(userId);
    }

    @Override
    public List<Map<String, Object>> list(Integer userId) {
        return this.baseMapper.list(userId);
    }

    @Override
    public List<Map<String, Object>> getGhztList(Integer userId) {
        List<Map<String,Object>>members = this.baseMapper.getGhztList(userId);
        for (int i = 0;i<members.size();i++){
            Map map = members.get(i);
            String user_Id = map.get("userId").toString();
            int count  = userContractMapper.getContractCount(Integer.valueOf(user_Id));
            if (count>0){
                map.put("contractName","活跃");
            }
        }
        return members ;
    }

    @Override
    public List<User> getUsersInUmbrella(Integer userId) {
        return this.baseMapper.getUsersInUmbrella(userId);
    }

    @Override
    public Integer getUnionCardNumber(Integer userId) {
        return this.baseMapper.getUnionCardNumber(userId);
    }
}
