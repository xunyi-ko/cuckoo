/**
 * 
 */
package site.xunyi.cuckoo.kafka;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import site.xunyi.cuckoo.data.Message;
import site.xunyi.cuckoo.websocket.Group;

/**
 * @author xunyi
 */
public class KafkaConsumerTask{
    /**
     * 线程池
     */
    private ThreadFactory factory = new ThreadFactory() {
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "GroupThread" + threadNumber.getAndIncrement());
            
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }

            return t;
        }
    };
    
    private ThreadPoolExecutor pool = new ThreadPoolExecutor(8, 160, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100), factory);
    private List<KafkaConsumerThread> threadList = new ArrayList<>();
    
    private final Object ONLINE = new Object();
    /**
     * 表示消费者是否在线
     */
    private Map<String, Object> customerNet = new ConcurrentHashMap<>(200);
    
    /**
     * 添加消费者
     * @param guid
     */
    public void addConsumer(String guid) {
        // 检查线程内消费者数量
        checkThread();
        
        // 将消费者加入到消费者最少的线程
        KafkaConsumerThread thread = getMinCustomerThread();
        thread.lock.lock();
        
        KafkaConsumerImpl consumer = new KafkaConsumerImpl(guid);
        thread.customers.add(consumer);
        
        customerNet.put(guid, ONLINE);
        thread.lock.unlock();
    }
    
    public void removeConsumer(String guid) {
        customerNet.remove(guid);
    }
    
    /**
     * 查看线程里的消费者数。如果单个线程的消费者数过多，则新建线程
     * 如果线程数过多，而线程内消费者少，则销毁线程，将消费者放入其他线程
     */
    // TODO
    private void checkThread() {
        
    }
    
    /**
     * 获取消费者最少的线程
     */
    private KafkaConsumerThread getMinCustomerThread() {
        KafkaConsumerThread min = null;
        for(KafkaConsumerThread kt : threadList) {
            if(min == null) {
                min = kt;
            }else {
                int size = kt.customers.size();
                if(size < min.customers.size()) {
                    min = kt;
                }
            }
        }
        return min;
    }
    
    private class KafkaConsumerThread implements Runnable{
        private List<KafkaConsumerImpl> customers = new ArrayList<>();
        private Map<String, List<String>> consumerTopics = new ConcurrentHashMap<>();
        private ReentrantLock lock = new ReentrantLock();
        
        private boolean stop = false;
        @Override
        public void run() {
            while(!stop) {
                lock.lock();
                
                try {
                    Iterator<KafkaConsumerImpl> iterator = customers.iterator();
                    while(iterator.hasNext()) {
                        KafkaConsumerImpl consumer = iterator.next();
                        if(customerNet.get(consumer.getAccount()) == ONLINE) {
                            List<Message> messages = consumer.receive(consumerTopics.get(consumer.getAccount()));
                            for(Message message : messages) {
                                Group.sendMessage(message);
                            }
                        }else {
                            iterator.remove();
                            consumerTopics.remove(consumer.getAccount());
                        }
                    }
                }catch (Exception e) {
                }finally {
                    lock.unlock();
                }
                
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
        private synchronized void stop() {
            this.stop = true;
        }
    }
}
