package org.example.datafulldisplay.service.ws;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ws/{userid}")
@Component
public class TransitionDataWebSocketServer {
    static Log log = LogFactory.get(TransitionDataWebSocketServer.class);

    private static int onlineCount = 0;

    private static final ConcurrentHashMap<String, TransitionDataWebSocketServer> websocketMap = new ConcurrentHashMap<>();

    private Session session;

    private String userid = "";

    public static void sendInfo(String message, @PathParam("userid") String userid) throws IOException{
        log.info("发送消息到:" + userid + "，报文:" + message);
        if (StringUtils.isNotBlank(userid) && websocketMap.containsKey(userid)){
            websocketMap.get(userid).sendMessage(message);
        }else {
            log.error("用户" + userid + ",不在线！");
        }
    }

    public static synchronized int fetchOnlineCount(){
        return onlineCount;
    }

    public static synchronized void addOnlineCount(){
        onlineCount++;
    }

    public static synchronized void subOnlineCount(){
        onlineCount--;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userid") String userid) {
        this.session = session;
        this.userid = userid;
        if (websocketMap.containsKey(userid)) {
            websocketMap.remove(userid);
            websocketMap.put(userid, this);
        } else {
            websocketMap.put(userid, this);
            addOnlineCount();
        }

        log.info("用户连接:" + userid + ",当前在线人数为:" + fetchOnlineCount());
        try {
            JSONObject message = new JSONObject();
            message.put("type", "connection");
            message.put("message", "连接成功");
            sendMessage(message.toJSONString());
        } catch (IOException e) {
            log.error("用户:" + userid + ",网络异常!!!!!!");
        }
    }

    @OnClose
    public void onClose() {
        if (websocketMap.containsKey(userid)) {
            websocketMap.remove(userid);
            subOnlineCount();
        }
        log.info("用户退出:" + userid + ",当前在线人数为:" + fetchOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户消息:" + userid + ",报文:" + message);
        if (StringUtils.isNotBlank(message)) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(message);
                jsonObject.put("fromUserId", this.userid);
                String toUserId = jsonObject.getString("toUserId");
                if (StringUtils.isNotBlank(toUserId) && websocketMap.containsKey(toUserId)){
                    websocketMap.get(toUserId).sendMessage(jsonObject.toJSONString());
                }else {
                    log.error("请求的userId:" + toUserId + "不在该服务器上");
                    //否则不在这个服务器上，发送到mysql或者redis
                }
                // 测试用
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.userid + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
}
