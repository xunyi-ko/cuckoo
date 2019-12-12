/**
 * 
 */
package site.xunyi.cuckoo.websocket;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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

import site.xunyi.cuckoo.entity.Customer;
import site.xunyi.cuckoo.entity.Message;
import site.xunyi.cuckoo.kafka.KafkaConsumerThread;
import site.xunyi.cuckoo.kafka.KafkaProducer;
import site.xunyi.cuckoo.service.CustomerService;

/**
 * @author xunyi
 */
@ServerEndpoint("/group/{guid}")
@Component
public class Group {
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private KafkaProducer kafkaProducer;
    
    /**
     * 线程池
     */
    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(8, 60, 1000, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20));
    
    /**
     * 有多少用户在登录状态
     */
    private static AtomicInteger count = new AtomicInteger();
    /**
     * 登录用户的socketSession
     */
    private static CopyOnWriteArraySet<Session> set = new CopyOnWriteArraySet<>();
    
    /**
     *  与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    
    /**
     * 根据唯一编号建立的映射，存放昵称
     */
    private static ConcurrentHashMap<String, String> names = new ConcurrentHashMap<>();
    
    /**
     * 消费者线程
     */
    private static ConcurrentHashMap<String, KafkaConsumerThread> threads = new ConcurrentHashMap<>();
    
    /**
     * 登录用户的唯一编号
     */
    private String guid;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("guid") String guid) {
        this.session = session;
        this.guid = guid;
        
        Customer custom = customerService.findByLogin(guid);
        names.put(guid, custom.getName());
        set.add(session);
        
        // 在线数加1
        addOnlineCount();
        
        //TODO
        KafkaConsumerThread runnable = new KafkaConsumerThread();
        pool.execute(runnable);
        threads.put(guid, runnable);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        set.remove(this.session);
        names.remove(this.guid);
        
        // 结束消费者线程
        KafkaConsumerThread runnable = threads.remove(this.guid);
        runnable.stop();
        pool.remove(runnable);
        
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
    public void sendMessage(Session session, String message, String name) throws IOException {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("msg", message);
        session.getBasicRemote().sendText(json.toJSONString());
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
