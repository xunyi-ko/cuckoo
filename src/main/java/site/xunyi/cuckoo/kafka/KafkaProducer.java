/**
 * 
 */
package site.xunyi.cuckoo.kafka;

import site.xunyi.cuckoo.entity.Message;

/**
 * @author xunyi
 */
public interface KafkaProducer {
    void send(String topic, Message message);
}
