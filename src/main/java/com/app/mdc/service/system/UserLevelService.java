package com.app.mdc.service.system;

import com.app.mdc.model.system.User;
import com.app.mdc.model.system.UserLevel;
import com.baomidou.mybatisplus.service.IService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserLevelService extends IService<UserLevel> {

    /**
     *  新增用户层级关系
     * @param recIds 推荐人id字符串集合
     * @param recedId 被推荐人id
     */
    void addLevelRelation(String recIds, String recedId);

    /**
     * 获取用户的被推荐人id
     * @param userId
     * @return
     */
    Map<Integer,  Map<String,Object>> selectRecedUserIds(Integer userId);


    /**
     * 获取用户的合约体量
     * @param userId
     * @return
     */
    BigDecimal getTotalSum(Integer userId);

    /**
     * 查询当前用户所有的推荐人
     * @param userId
     * @return
     */
    List<Integer> selectRecIdsByRecedId(Integer userId);

    /**
     * 获取直推人数
     * @param userId
     * @return
     */
    Integer getDirectCount(Integer userId);

    /**
     * 获取直推用户的信息
     * @param userId
     * @return
     */
    List<User> getDirectUsers(Integer userId);

    /**
     * 获取用户的团队数量
     * @param userId
     * @return
     */
    Integer selectMemberSizeByUserId(String userId);

    /**
     * 查询用户的工会成员
     * @param userId
     * @return
     */
    List<Map<String, Object>> list(Integer userId);

    /**
     * 查询工会直推
     * @param userId
     * @return
     */
    List<Map<String, Object>> getGhztList(Integer userId);

    /**
     * 查询伞下用户信息
     * @param userId
     * @return
     */
    List<User> getUsersInUmbrella(Integer userId);

    /**
     * 获取用户合约卡数
     * @param userId
     * @return
     */
    Integer getUnionCardNumber(Integer userId);
}
