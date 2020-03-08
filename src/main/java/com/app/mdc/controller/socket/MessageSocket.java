package com.app.mdc.controller.socket;

import com.alibaba.fastjson.JSON;
import com.app.mdc.model.socket.Message;
import com.app.mdc.service.socket.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.websocket.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户消息socket
 */
public class MessageSocket {
    private static Logger logger = LoggerFactory.getLogger(MessageSocket.class);

    private EndpointConfig config;
    public static Map<String, List<Session>> users = new HashMap<>();

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        MessageSocket.applicationContext = applicationContext;
    }

    /**
     * 获取消息接口
     * @param message message
     * @param session socketSession
     * @throws IOException io
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        Message messageModel = JSON.parseObject(message, Message.class);

        MessageService messageService = getApplicationContext().getBean(MessageService.class);
        switch (messageModel.getType()){
            case 1:
                //{"type":1,"sendUserId":"12023e9ea0fb45bfa3f6d5add283b6c0","receiveUserId":"59b44781f03f4d75b0bc3bfeeb17282f","content":"时间！！！！！！","messageType":1}
                messageService.saveMessageFromP2PSocket(messageModel);
                break;
            case 2:
                //{"type":2,"sendUserId":"12023e9ea0fb45bfa3f6d5add283b6c0","teamId":"1111111111","content":"群聊消息插入","messageType":1}
                messageService.saveMessageFromTeamSocket(messageModel);
                break;
            case 3:
                //{"type":3,"sendUserId":"12023e9ea0fb45bfa3f6d5add283b6c0","messageId":"12023e9ea0fb45bfa3f6d5add283b6c0"}
                Map info = JSON.parseObject(message, Map.class);
                messageService.updateMessageReadStatus(info);
                break;
            default:
                break;
        }
    }

    /**
     * 用户连接socket服务器
     * @param config EndpointConfig
     * @param session socketSession
     */
    @OnOpen
    public void onOpen(EndpointConfig config, Session session) {
        this.config = config;
        String UserKey = config.getUserProperties().get("UserKey").toString();
        if (users.get(UserKey) == null || users.get(UserKey).size() == 0){
            List<Session> sessions = new ArrayList<>();
            sessions.add(session);
            users.put(UserKey, sessions);
        }else{
            users.get(UserKey).add(session);
        }
        System.out.println("MessageSocket：【" + UserKey + "】已连接！");
    }

    /**
     * 用户断开socket服务器
     */
    @OnClose
    public void onClose(Session session) {
        String UserKey = config.getUserProperties().get("UserKey").toString();
        if (users.get(UserKey) != null && users.get(UserKey).size() != 0){
            List<Session> sessions = users.get(UserKey);
            sessions = sessions.stream()
                    .filter(session1 -> !session1.getId().equals(session.getId()))
                    .collect(Collectors.toList());
            users.put(UserKey, sessions);
            System.out.println("MessageSocket：【" + UserKey + "】已离开！");
        }
    }
}
