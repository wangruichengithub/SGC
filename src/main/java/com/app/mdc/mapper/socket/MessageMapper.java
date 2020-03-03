package com.app.mdc.mapper.socket;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.app.mdc.model.socket.Message;
import com.app.mdc.model.system.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since 2019-07-23
 */
@Component
public interface MessageMapper extends BaseMapper<Message> {

    /**
     * 根据当前用户获得该用户点对点聊天的用户列表
     * @param currentUser 当前用户
     * @return userList
     */
    List<User> findMessageUsersByCurrentUser(User currentUser);

    /**
     * 根据会话Id，获取该会话的聊天记录
     * @param type 类型，1：点对点，2：群聊
     * @param teamId 群聊Id
     * @param sendUserId 点对点用户Id
     * @param currentUserId 当前用户Id
     * @return MessageList
     */
    List<Message> getMessageByChat(@Param("type") String type,
                                   @Param("teamId") String teamId,
                                   @Param("sendUserId") String sendUserId,
                                   @Param("currentUserId") String currentUserId);

    /**
     * 更具当前用户获取消息列表
     * @param currentUser 当前用户
     * @return List
     */
    List<Map<String, Object>> findmessageChartList(User currentUser);

    /**
     * 取料消息已读确认
     * @param message message
     */
    void insertTeamMessageRead(Map<String, Object> message);
}
