/**
 * 
 */
package site.xunyi.cuckoo.kafka;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecords;

import site.xunyi.cuckoo.entity.Message;

/**
 * @author xunyi
 */
public class KafkaConsumerImpl implements KafkaConsumer<Message>{
    private static final Properties prop;
    private static final Duration DURATION = Duration.ofSeconds(1);
    
    org.apache.kafka.clients.consumer.KafkaConsumer<String, Message> c = new org.apache.kafka.clients.consumer.KafkaConsumer<String, Message>(prop);
    static {
        prop = new Properties();
        prop.put("bootstrap.servers", "192.168.59.146:9092");
        prop.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        prop.put("group.id","test_group4");
        prop.put("client.id", "demo-consumer-client");
        prop.put("auto.offset.reset", "earliest");
        prop.put("enable.auto.commit", "true"); //默认为true自动提交
        prop.put("auto.commit.interval.ms", "10000"); //设置默认自动提交时间 ，默认值为5000ms
    }
    
    @Override
    public List<Message> receive(Collection<String> topics) {
        c.subscribe(topics);
        ConsumerRecords<String, Message> records = c.poll(DURATION);
        
        List<Message> res = new ArrayList<>(records.count());
        if(!records.isEmpty()) {
            records.forEach(record -> {
                res.add(record.value());
            });
        }
        return res;
    }
}
