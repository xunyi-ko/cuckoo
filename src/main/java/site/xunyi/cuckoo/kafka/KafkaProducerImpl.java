/**
 * 
 */
package site.xunyi.cuckoo.kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import site.xunyi.cuckoo.entity.Message;

/**
 * @author xunyi
 */
@Service
public class KafkaProducerImpl implements KafkaProducer{
    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;
    
    //发送消息方法
    public void send(String topic, Message message) {
        kafkaTemplate.send(topic, message);
    }
}
