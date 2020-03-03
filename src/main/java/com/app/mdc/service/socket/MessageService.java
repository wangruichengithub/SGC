package com.app.mdc.service.socket;

import com.baomidou.mybatisplus.service.IService;
import com.app.mdc.model.socket.Message;
import com.app.mdc.model.system.User;
import com.app.mdc.utils.viewbean.Page;
import com.app.mdc.utils.viewbean.ResponseResult;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since 2019-07-23
 */
public interface MessageService extends IService<Message> {

    /**
     * 保存消息，点对点
     * @param message message
     */
    void saveMessageFromP2PSocket(Message message) throws IOException;

    /**
     * 群聊消息
     * @param messageModel message
     */
    void saveMessageFromTeamSocket(Message messageModel) throws IOException;

    /**
     * 获取用户消息记录
     * @param messageMap message
     * @return Messages
     */
    List<Message> findMessages(Map messageMap);

    /**
     * 获取曾经聊天过的用户列表
     * @param currentUser 当前用户
     * @return Users
     */
    List<User> findMessageUsers(User currentUser);

    /**
     * 获取聊天信息接口。
     * @param page page分页
     * @param type 类型，1：点对点，2：群聊
     * @param teamId 群聊Id
     * @param sendUserId 点对点用户Id
     * @param user 当前用户
     */
    ResponseResult getMessageByChat(Page page, String type, String teamId, String sendUserId, User user);

	/**
	 *app推送
	 * @param param	username	title	text
	 * @return	推送结果
	 */
	String msgPush(Map<String,Object> param);

    /**
     * 根据当前用户获取message消息列表
     * @param currentUser 当前用户
     * @return List
     */
    List<Map<String, Object>> findMessagesList(User currentUser);

    /**
     * 更新用户信息已读未读状态
     * type： 3：消息已读
     * userid：当前用户id
     * messageId： 消息id
     * @param info message
     */
    void updateMessageReadStatus(Map info);
}
