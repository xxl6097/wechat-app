package com.wxcloudrun.demo.ws;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Collection;

public class WebScoketHelper {
    private final Logger logger = LoggerFactory.getLogger(WebScoketHelper.class);

    /**
     * 推送文本消息给指定的客户端
     */
    public static void sendMessage(Session sess, String message) throws IOException {
        sess.getBasicRemote().sendText(message);
    }

    /**
     * 推送对象给指定的客户端
     * @param sess:
     * @param object:
     */
    public static void sendObject(Session sess, Object object) throws IOException, EncodeException {
        sess.getBasicRemote().sendObject(object);
    }

    /**
     * 推送文本给所有的客户端
     */
    public static void sendTextToAll(Collection<Session> sessionList, String message) {

        sessionList.forEach(e -> {
            try {
                sendMessage(e, message);
            } catch (IOException e1 ) {
                throw new RuntimeException("推送失败，session.id=" + e.getId(), e1);
            }
        });
    }

    /**
     * 推送文本给所有的客户端
     */
    public static void sendObjectToAll(Collection<Session> sessionList, String message) {

        sessionList.forEach(e -> {
            try {
                sendObject(e, message);
            } catch (IOException | EncodeException e1) {
                throw new RuntimeException("推送失败，session.id=" + e.getId(), e1);
            }
        });
    }
}
