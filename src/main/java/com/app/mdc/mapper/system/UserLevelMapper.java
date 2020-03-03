package com.app.mdc.mapper.system;

import com.app.mdc.model.system.User;
import com.app.mdc.model.system.UserLevel;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public interface UserLevelMapper extends BaseMapper<UserLevel> {

    /**
     * 批量新增用户层级关系
     * @param userLevels
     */
    void batchInsert(@Param("list") List<UserLevel> userLevels);

    /**
     * 获取被推荐用户的所有ids
     * @param userId
     * @return
     */
    @MapKey("level")
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
     * 获取直推用户信息
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
     * 查询用户工会成员列表
     * @param userId
     * @return
     */
    List<Map<String, Object>> list(Integer userId);

    /**
     * 查询直推列表
     * @param userId
     * @return
     */
    List<Map<String,Object>> getGhztList(Integer userId);

    /**
     * 查询伞下用户信息
     * @param userId
     * @return
     */
    List<User> getUsersInUmbrella(Integer userId);

    /**
     * 获取团队合约卡数
     * @param userId
     * @return
     */
    Integer getUnionCardNumber(Integer userId);
}
