package site.xunyi.cuckoo.websocket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@ServerEndpoint("/normal/{name}/{roomId}")
@Component
public class WebSocketServer {
	/**
	 *  静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	 */
	private static ConcurrentHashMap<String, AtomicInteger> countMap = new ConcurrentHashMap<>();
	/**
	 *  concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
	 */
	private static ConcurrentHashMap<String, CopyOnWriteArraySet<Session>> socketMap = new ConcurrentHashMap<>();

	/**
	 * 存放名称
	 */
	private static ConcurrentHashMap<String, String> nameMap = new ConcurrentHashMap<>();
	
	/**
	 * 存放房间id
	 */
	private static ConcurrentHashMap<String, String> roomIdMap = new ConcurrentHashMap<>();
	/**
	 *  与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	private Session session;

	/**
	 * 连接建立成功调用的方法
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("name") String name, @PathParam("roomId") String roomId) {
		this.session = session;
		
		CopyOnWriteArraySet<Session> sessionSet = socketMap.get(roomId);
		if(sessionSet == null) {
		    sessionSet = new CopyOnWriteArraySet<Session>();
		    socketMap.put(roomId, sessionSet);
		}
		nameMap.put(session.getId(), name);
		roomIdMap.put(session.getId(), roomId);
		
		// 加入set中
		sessionSet.add(session);
		// 在线数加1
		addOnlineCount(roomId);
		onMessage(name + "进入" + roomId + "房间", session);
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
	    String roomId = getRoomId();
		// 从set中删除
	    CopyOnWriteArraySet<Session> sessionSet = socketMap.get(roomId);
	    sessionSet.remove(this.session);
	    
	    nameMap.remove(session.getId());
	    roomIdMap.remove(session.getId());
		// 在线数减1
		subOnlineCount(roomId);
	}

	/**
	 * 收到客户端消息后调用的方法
	 *
	 * @param message 客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
	    String name = getName();
	    String roomId = getRoomId();
	    
		// 群发消息
		CopyOnWriteArraySet<Session> sessionSet = socketMap.get(roomId);
		for (Session item : sessionSet) {
			try {
				sendMessage(item, message, name);
			} catch (IOException e) {
				e.printStackTrace();
			}
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
	public void sendMessage(Session session, String message, String name) throws IOException {
		JSONObject json = new JSONObject();
		json.put("name", name);
		json.put("msg", message);
		session.getBasicRemote().sendText(json.toJSONString());
	}

	public static int getOnlineCount(String roomId) {
        AtomicInteger onlineCount = countMap.get(roomId);
		return onlineCount.get();
	}

	public static void addOnlineCount(String roomId) {
	    AtomicInteger onlineCount = countMap.get(roomId);
	    if(onlineCount == null) {
	        onlineCount = new AtomicInteger();
	        countMap.put(roomId, onlineCount);
	    }
	    onlineCount.incrementAndGet();
	}

	public static void subOnlineCount(String roomId) {
        AtomicInteger onlineCount = countMap.get(roomId);
        if(onlineCount == null) {
            onlineCount = new AtomicInteger();
            countMap.put(roomId, onlineCount);
        }
		onlineCount.decrementAndGet();
	}
	
	private String getRoomId() {
	    return roomIdMap.get(this.session.getId());
	}
	private String getName() {
	    return nameMap.get(this.session.getId());
	}
}
