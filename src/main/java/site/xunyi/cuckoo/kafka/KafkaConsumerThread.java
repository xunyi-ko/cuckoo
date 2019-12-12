/**
 * 
 */
package site.xunyi.cuckoo.kafka;

/**
 * @author xunyi
 */
public class KafkaConsumerThread implements Runnable{
    boolean isStop;
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        
    }
    
    public void stop() {
        this.isStop = true;
    }
}
