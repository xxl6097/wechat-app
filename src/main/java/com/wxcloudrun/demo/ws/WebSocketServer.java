package com.wxcloudrun.demo.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket/test/{sid}",encoders = ObjectMessageEncoder.class)
@Component
public class WebSocketServer {
    private final Logger log = LoggerFactory.getLogger(WebScoketHelper.class);
    /**
     * 每个客户端对应的WebSocket对象
     */
    public static Map<String, Session> clientMap = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        clientMap.put(sid, session);

        log.info("有一个新的客户端 : " + sid + " , 当前客户端连接总数: " + clientMap.size());
        try {
            WebScoketHelper.sendMessage(session, "你好，新客户端！sid = " + sid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        clientMap.remove(sid);
        log.info("有一个客户端断开，当前客户端连接总数: " + clientMap.size());
    }

    /**
     * 收到客户端消息
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid) {
        log.info("接收到客户端消息, " + sid +" , message: " + message);
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("出错了！");
        error.printStackTrace();
    }

}
