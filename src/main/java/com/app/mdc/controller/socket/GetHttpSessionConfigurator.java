package com.app.mdc.controller.socket;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.List;
import java.util.Map;

public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator{

    @Override
    public void modifyHandshake(ServerEndpointConfig sec,
                                HandshakeRequest request, HandshakeResponse response) {

        //获取存储用户id的信息
        Map<String, List<String>> map = request.getParameterMap();
        String userInfo = map.get("userid").get(0);
        sec.getUserProperties().put("UserKey", userInfo);
    }

}
