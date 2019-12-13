/**
 * 
 */
package site.xunyi.cuckoo.websocket;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import site.xunyi.cuckoo.entity.Message;
import site.xunyi.cuckoo.kafka.KafkaConsumerTask;
import site.xunyi.cuckoo.kafka.KafkaProducer;

/**
 * @author xunyi
 */
@ServerEndpoint("/group/{guid}")
@Component
public class Group {
    @Autowired
    private KafkaProducer kafkaProducer;
    
    /**
     * 消费者任务
     */
    private KafkaConsumerTask task = new KafkaConsumerTask();
    /**
     * 有多少用户在登录状态
     */
    private static AtomicInteger count = new AtomicInteger();
    /**
     * 登录用户的socketSession
     */
    private static ConcurrentHashMap<String, Session> map = new ConcurrentHashMap<>();
    
    /**
     * 登录用户的唯一编号
     */
    private String guid;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("guid") String guid) {
        this.guid = guid;
        
        map.put(guid, session);
        
        // 在线数加1
        addOnlineCount();
        task.addConsumer(guid);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        map.remove(this.guid);
        
        // 结束消费者线程
        task.removeConsumer(guid);
        
        // 在线数减1
        subOnlineCount();
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        JSONObject json = JSONObject.parseObject(message);
        
        String msg = json.getString("msg");
        String groupId = json.getString("groupId");
        
        Message m = new Message();
        m.setMsg(msg);
        m.setSendTime(new Date());
        kafkaProducer.send(groupId, m);
    }

    /**
     * 
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public static void sendMessage(Message message) throws IOException {
        Session session = map.get(message.getAccount());
        session.getBasicRemote().sendText(JSONObject.toJSONString(message));
    }

    public static int getOnlineCount(String roomId) {
        return count.get();
    }

    public static void addOnlineCount() {
        count.incrementAndGet();
    }

    public static void subOnlineCount() {
        count.decrementAndGet();
    }
}
