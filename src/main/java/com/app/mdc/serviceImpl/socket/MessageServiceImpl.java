package com.app.mdc.serviceImpl.socket;

import com.alibaba.fastjson.JSON;
import com.app.mdc.config.PicConfig;
import com.app.mdc.controller.socket.MessageSocket;
import com.app.mdc.mapper.socket.MessageMapper;
import com.app.mdc.mapper.socket.TeamMapper;
import com.app.mdc.mapper.system.UserMapper;
import com.app.mdc.model.socket.Message;
import com.app.mdc.model.system.User;
import com.app.mdc.service.socket.MessageService;
import com.app.mdc.utils.httpclient.HttpUtil;
import com.app.mdc.utils.viewbean.Page;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.websocket.Session;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author
 * @since 2019-07-23
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    private final MessageMapper messageMapper;
    private final UserMapper userMapper;
    private final TeamMapper teamMapper;
    private final PicConfig picConfig;

    public MessageServiceImpl(MessageMapper messageMapper, UserMapper userMapper, TeamMapper teamMapper, PicConfig picConfig) {
        this.messageMapper = messageMapper;
        this.userMapper = userMapper;
        this.teamMapper = teamMapper;
		this.picConfig = picConfig;
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMessageFromP2PSocket(Message message) throws IOException {
        initModel(message);
        messageMapper.insert(message);

        //推送消息
        sendMessage(message, message.getReceiveUserId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMessageFromTeamSocket(Message messageModel) throws IOException {
        initModel(messageModel);
        messageMapper.insert(messageModel);

        String teamId = messageModel.getTeamId();

        //该消息当前用户设置为已读
        Map<String, Object> messageUpdate = new HashMap<>();
        messageUpdate.put("messageId", messageModel.getId());
        messageUpdate.put("userId", messageModel.getSendUserId());
        messageUpdate.put("teamId",  teamId);
        messageMapper.insertTeamMessageRead(messageUpdate);

        //获取群聊中的人，然后分发消息
        List<User> users = teamMapper.findTeamUsersByTeamId(teamId);
        for (User user: users){
            //群聊不推送自己的
            if (user.getId().equals(messageModel.getSendUserId())){
                continue;
            }
            sendMessage(messageModel, user.getId());
        }
    }

    /**
     * 推送消息
     * @param message message
     * @param userId userId用户id
     * @throws IOException io
     */
    private void sendMessage(Message message, String userId) throws IOException {
        List<Session> sessions = MessageSocket.users.get(userId);
        //该用户不在线，不推送
        if ( sessions != null && sessions.size() != 0){
            //判断session集合中所有打开中的session
            sessions = sessions.stream().filter(Session::isOpen).collect(Collectors.toList());
            for(Session session : sessions){
                session.getBasicRemote().sendText(
                        JSON.toJSONStringWithDateFormat(message, "yyyy-MM-dd HH:mm:ss")
                );
            }
            MessageSocket.users.put(userId, sessions);
        }else {
        	//如果当前用户不在聊天页面，消息推送
			Map<String,Object> push=new HashMap<>();
			push.put("userId",userId);
			push.put("title",message.getUserName());
			if(message.getMessageType() == 1){
                push.put("text",message.getContent());
            }else if(message.getMessageType() == 2){
                push.put("text","[图片]");
            }else if(message.getMessageType() == 3){
                push.put("text","[语音]");
            }else if(message.getMessageType() == 4){
                push.put("text","[视频]");
            }else if(message.getMessageType() == 5){
                push.put("text","[文件]");
            }
			msgPush(push);
		}
    }

    /**
     * 生成model类（初始化，赋值）
     * @param messageModel messageModel
     */
    private void initModel(Message messageModel){
        Date date = new Date();
        messageModel.setCreatetime(date);
        messageModel.setUpdatetime(date);
        messageModel.setDeleted(0);

        User user = userMapper.selectById(messageModel.getSendUserId());
        messageModel.setUserName(user.getUserName());
    }

    @Override
    public List<Message> findMessages(Map messageMap) {
        return new ArrayList<>();
    }

    @Override
    public List<User> findMessageUsers(User currentUser) {
        return messageMapper.findMessageUsersByCurrentUser(currentUser);
    }

    @Override
    public ResponseResult getMessageByChat(Page page, String type, String teamId, String sendUserId, User user) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<Message> list = messageMapper.getMessageByChat(type, teamId, sendUserId, user.getId());
        return ResponseResult.success().add(new PageInfo<>(list));
    }

    @Override
    public String msgPush(Map<String, Object> param) {
    	//1.要发送消息的人员username，可以多发，多发就重新拼接
		String userId = param.get("userId").toString();
		String[] userIds = userId.split(",");

		EntityWrapper<User> userEntityWrapper=new EntityWrapper<>();
		userEntityWrapper.in("id",userIds).eq("deleted",0);
		List<User> userList=userMapper.selectList(userEntityWrapper);
		//找出所有用户cid拼接
		List<String> cids=userList.stream().map(User::getUpUserId).collect(Collectors.toList());
		cids = cids.stream().filter(Objects::nonNull).filter(StringUtils::isNotEmpty).collect(Collectors.toList());
		String CID=String.join(",",cids);

		//2.给推送接口的值
		HashMap<String, String> para = new HashMap<>();
		para.put("APPID", picConfig.getAPPID());
		para.put("APPKEY", picConfig.getAPPKEY());
		para.put("MASTERSECRET", picConfig.getMASTERSECRET());
		para.put("title", param.get("title").toString());
		para.put("text", param.get("text").toString());
		para.put("CID", CID);

        return HttpUtil.doPost(picConfig.getMsgPushUrl(), para,null);
    }

    @Override
    public List<Map<String, Object>> findMessagesList(User currentUser) {
        return messageMapper.findmessageChartList(currentUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMessageReadStatus(Map info) {
        String userId = info.get("userid").toString();
        String messageId = info.get("messageId").toString();

        Message message = messageMapper.selectById(messageId);
        if (message.getType() == 1){
            message.setUpdatetime(new Date());
            message.setReadstatus(1);
            messageMapper.updateById(message);
        }else{
            Map<String, Object> messageUpdate = new HashMap<>();
            messageUpdate.put("messageId", messageId);
            messageUpdate.put("userId", userId);
            messageUpdate.put("teamId",  message.getTeamId());
            messageMapper.insertTeamMessageRead(messageUpdate);
        }
    }
}
