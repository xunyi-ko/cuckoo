/**
 * 
 */
package site.xunyi.cuckoo.websocket;

import java.io.IOException;
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

import kafka.admin.TopicCommand;
import kafka.admin.TopicCommand;
import site.xunyi.cuckoo.data.Message;
import site.xunyi.cuckoo.data.Message.Type;
import site.xunyi.cuckoo.entity.Room;
import site.xunyi.cuckoo.kafka.KafkaConsumerTask;
import site.xunyi.cuckoo.kafka.KafkaProducer;
import site.xunyi.cuckoo.util.GuidUtil;
import site.xunyi.cuckoo.util.GuidUtil.EntityType;

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
        Message m = JSONObject.parseObject(message, Message.class);
        
        if(m.getType() != null && m.getType() == Type.CREATE_ROOM.ordinal()) {
            // 创建房间
            Room room = new Room();
            room.setGuid(GuidUtil.initGuid(EntityType.ROOM));
            room.setOwner(m.getAccount());
            room.setName(m.getRoomName());
            
            // TODO 保存房间
            
            
            // 创建主题
            TopicCommand.main(new String[] {
                "--create", 
                "--zookeeper","localhost:2181",
                "--partitions", "1", 
                "--topic", room.getGuid(),
                "--replication-factor", "1" 
            });
            
            // 房间创建成功，需要给页面反馈
            try {
                session.getBasicRemote().sendText("success");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            // 发送消息
            kafkaProducer.send(m.getRoomGuid(), m);
        }
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
        if(session == null) {
            return;
        }
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
