package com.app.mdc.service.socket;

import com.app.mdc.exception.BusinessException;
import com.baomidou.mybatisplus.service.IService;
import com.app.mdc.model.socket.Team;
import com.app.mdc.model.system.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since 2019-07-23
 */
public interface TeamService extends IService<Team> {

    /**
     * 获取我的聊天群聊
     * @param currentUser 当前用户
     * @return Team
     */
    List<Team> findMyTeams(User currentUser);

    /**
     *  @param teamId 群组Id
     * @param teamName 群聊名称
     * @param userIds 用户ids集合，逗号分隔
     */
    Team saveOrUpdateSocketTeam(String teamId, String teamName, String userIds) throws BusinessException;

    /**
     * 根据群聊id获取该群聊下所有人
     * @param teamId 群聊id
     * @return userlist
     */
    List<User> getUsersByTeam(String teamId);
}
